'use strict';

import React from "react";
import {Button, Checkbox, ControlLabel, FormControl, FormGroup, Modal} from "react-bootstrap";
import client from "../client";
import update from "react-addons-update";

class PostModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            opened: props.opened !== 'undefined' ? props.opened : true,
            post: props.post
        };

        this.closeModal = this.closeModal.bind(this);
        this.submitNewsPost = this.submitNewsPost.bind(this);
    }

    componentDidMount() {
        let request = {};
        if (this.state.post) {
            request = {
                method: 'POST',
                path: '/posts/create',
                entity: this.state.post,
                headers: {
                    'Content-Type': 'application/json'
                }
            };
        } else {
            request = {
                method: 'GET',
                path: '/posts/new',
                headers: {
                    'Content-Type': 'application/json'
                }
            };
        }
        client(request)
            .done(response => {
                this.setState({
                    newsPost: response.entity,
                });
            });

    }

    closeModal() {
        this.setState({opened: false})
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
                this.setState({errorModal: true})
            }
        });
    }

    closeModal() {
        console.log("Close!!!");
    }

    render() {
        return (
            <div>
                <Modal show={this.state.opened} onHide={this.closeModal}>
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
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {title: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Category</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.category ? this.state.newsPost.category : ""}
                                    placeholder="Enter Category"
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {category: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Photo URL</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.mainPhoto ? this.state.newsPost.mainPhoto : ""}
                                    placeholder="Enter URL"
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {mainPhoto: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>HTML Text</ControlLabel>
                                <FormControl
                                    componentClass="textarea"
                                    rows={20}
                                    value={this.state.newsPost.htmlText ? this.state.newsPost.htmlText : ""}
                                    placeholder="Enter Text"
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {htmlText: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Checkbox
                                    checked={!!this.state.newsPost.visibleInSearchEngines}
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {visibleInSearchEngines: {$set: e.target.checked}})})}
                                >Visible in search</Checkbox>
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Tags</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.tags ? this.state.newsPost.tags : ""}
                                    placeholder="Enter Tags. Comma separated"
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {tags: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Visibility</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.visibility ? this.state.newsPost.visibility : ""}
                                    placeholder="Enter visibility"
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {visibility: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Comments Rights</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.commentsRights ? this.state.newsPost.commentsRights : ""}
                                    placeholder="Enter comment rights"
                                    onChange={() => this.setState({newsPost: update(this.state.newsPost, {commentsRights: {$set: e.target.value}})})}
                                />
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.closeModal}>Close</Button>
                        <Button bsStyle="primary" onClick={this.submitNewsPost}>Submit</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }

}

export default PostModal;