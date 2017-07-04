'use strict';

import React from "react";
import {Button, Checkbox, Collapse, ControlLabel, FormControl, FormGroup, Modal} from "react-bootstrap";
import client from "../client";
import update from "react-addons-update";
import autoBind from "class-autobind";
import type {EditorValue} from "react-rte";
import RichTextEditor, {createEmptyValue, createValueFromString} from "react-rte";

type Props = {};
type State = {
    value: EditorValue,
    opened: Boolean,
    newsPost: NewsPost,
    showHtmlSource: Boolean,
    linksPreview: OpenGraph
};

type NewsPost = {
    id: String,
    title: String,
    category: String,
    mainPhoto: String,
    htmlText: String,
    tags: String,
    visibility: String,
    commentsRights: String
}

type OpenGraph = {
    title: String,
    photo: String,
    description: String
}

export default class NewsModal extends React.Component {
    props: Props;
    state: State;

    constructor(props) {
        super(props);
        this.props = props;
        autoBind(this);
        this.state = {
            value: createEmptyValue(),
            opened: false,
            newsPost: {},
            showHtmlSource: false
        };
    }

    close() {
        this.setState({opened: false})
    }

    open(newsPost) {
        let oldValue = this.state.value;
        this.setState({
            newsPost: newsPost,
            value: oldValue.setContentFromString(newsPost.htmlText, 'html'),
            opened: true
        });
    }

    submitNewsPost() {
        let originalLink = this.state.newsPost.mainPhoto;
        let encodedUri = encodeURIComponent(originalLink);
        this.setState({newsPost: update(this.state.newsPost, {mainPhoto: {$set: encodedUri}})});
        client({
            method: 'POST',
            path: '/posts/save',
            entity: this.state.newsPost,
            headers: {
                'Content-Type': 'application/json'
            }
        }).done(response => {
            if (response.status.code === 200) {
                this.setState({
                    opened: false,
                    successModal: true
                });
                this.props.onNewsModalPosted(this.state.newsPost);
            } else {
                this.setState({errorModal: true,
                    newsPost: update(this.state.newsPost, {mainPhoto: {$set: originalLink}})
                });
            }
        });
    }

    render() {
        let {value} = this.state;
        return (
            <div>
                <Modal show={this.state.opened} onExit={this.close}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create news post</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <form>
                            <FormGroup>
                                <ControlLabel>Title</ControlLabel>
                                <FormControl
                                    componentClass="textarea"
                                    rows={4}
                                    value={this.state.newsPost.title || ""}
                                    placeholder="Enter Title"
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {title: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Category</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.category || ""}
                                    placeholder="Enter Category"
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {category: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Photo URL</ControlLabel>
                                <p style={{
                                    textAlign: 'center',
                                    display: this.state.newsPost.mainPhoto ? "block" : "none"
                                }}>
                                    <img src={this.state.newsPost.mainPhoto} style={{width: '160px'}}/>
                                </p>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.mainPhoto || ""}
                                    placeholder="Enter URL"
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {mainPhoto: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>HTML Text</ControlLabel>
                                <RichTextEditor
                                    value={value}
                                    onChange={(value) => this.setState(
                                        {
                                            newsPost: update(this.state.newsPost, {htmlText: {$set: value.toString('html')}}),
                                            value: value
                                        }
                                        )}
                                    className="react-rte"
                                    placeholder="Write news post"
                                    toolbarClassName="toolbar"
                                    editorClassName="editor"
                                />
                                <Button onClick={() => this.setState({showHtmlSource: !this.state.showHtmlSource}) }>
                                    Toggle html code
                                </Button>
                                <Collapse in={this.state.showHtmlSource}>
                                    <FormControl
                                        componentClass="textarea"
                                        rows={20}
                                        value={this.state.newsPost.htmlText || ""}
                                        placeholder="Enter Text"
                                        onChange={(e) => this.setState({
                                            newsPost: update(this.state.newsPost, {htmlText: {$set: e.target.value}}),
                                            value: value.setContentFromString(e.target.value, 'html')
                                        })}
                                    />
                                </Collapse>
                            </FormGroup>
                            <FormGroup>
                                <Checkbox
                                    checked={!!this.state.newsPost.visibleInSearchEngines}
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {visibleInSearchEngines: {$set: e.target.checked}})})}
                                >Visible in search</Checkbox>
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Tags</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.tags || ""}
                                    placeholder="Enter Tags. Comma separated"
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {tags: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Visibility</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.visibility || ""}
                                    placeholder="Enter visibility"
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {visibility: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Comments Rights</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.commentsRights || ""}
                                    placeholder="Enter comment rights"
                                    onChange={(e) => this.setState({newsPost: update(this.state.newsPost, {commentsRights: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.close}>Close</Button>
                        <Button bsStyle="primary" onClick={this.submitNewsPost}>Submit</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}