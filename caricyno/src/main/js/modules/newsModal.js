'use strict';

import React from "react";
import {Button, Checkbox, ControlLabel, FormControl, FormGroup, Modal, Collapse} from "react-bootstrap";
import client from "../client";
import update from "react-addons-update";
import autoBind from "class-autobind";
import type {EditorValue} from "react-rte";
import RichTextEditor, {createEmptyValue, createValueFromString} from "react-rte";

type Props = {};
type State = {
    value: EditorValue,
    opened: Boolean,
    newsPost: {},
    showHtmlSource: Boolean
};

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


        this.close = this.close.bind(this);
        this.open = this.open.bind(this);
        this.submitNewsPost = this.submitNewsPost.bind(this);
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
                    opened: () => {
                        return false
                    }, successModal: true
                });
            } else {
                this.setState({errorModal: true});
                this.state.post.isPublished = true;
                this.props.onModalPublishedChange(this.state.post);
            }
        });
    }

    render() {
        let {value} = this.state;
        return (
            <div>
                <Modal show={this.state.opened} onHide={this.close}>
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