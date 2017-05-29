'use strict';

const React = require('react');
const {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl, Checkbox} = require('react-bootstrap');
const client = require('../client');
import update from "react-addons-update";

class PostModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {modal: props.modal, newsPost: props.newsPost};
        this.state = {
            filterText: '',
            inStockOnly: false
        };
        this.closeModal = this.closeModal.bind(this);
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleCategoryChange = this.handleCategoryChange.bind(this);
        this.handleMainPhotoChange = this.handleMainPhotoChange.bind(this);
        this.handleHtmlTextChange = this.handleHtmlTextChange.bind(this);
        this.handleVisibleInSearchChange = this.handleVisibleInSearchChange.bind(this);
        this.handleTagsChange = this.handleTagsChange.bind(this);
        this.handleVisibilityChange = this.handleVisibilityChange.bind(this);
        this.handleCommentsRightsChange = this.handleCommentsRightsChange.bind(this);
        this.submitNewsPost = this.submitNewsPost.bind(this);
    }

    closeModal() {
        this.setState({modal: false})
    }

    handleTitleChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {title: {$set: e.target.value}})});
    }

    handleCategoryChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {category: {$set: e.target.value}})});
    }

    handleMainPhotoChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {mainPhoto: {$set: e.target.value}})});
    }

    handleHtmlTextChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {htmlText: {$set: e.target.value}})});
    }

    handleVisibleInSearchChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {visibleInSearchEngines: {$set: e.target.checked}})});
    }

    handleTagsChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {tags: {$set: e.target.value}})});
    }

    handleVisibilityChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {visibility: {$set: e.target.value}})});
    }

    handleCommentsRightsChange(e) {
        this.setState({newsPost: update(this.state.newsPost, {commentsRights: {$set: e.target.value}})});
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
            if (response.status.code == 200) {
                this.setState({modal: false, successModal: true});
            } else {
                this.setState({errorModal: true})
            }
        });
    }

    render() {
        return (
            <div>
                <Modal show={this.state.modal} onHide={this.closeModal}>
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
                                    value={this.state.newsPost.title ? this.state.newsPost.title : ""}
                                    placeholder="Enter Title"
                                    onChange={this.handleTitleChange}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Category</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.category ? this.state.newsPost.category : ""}
                                    placeholder="Enter Category"
                                    onChange={this.handleCategoryChange}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Photo URL</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.mainPhoto ? this.state.newsPost.mainPhoto : ""}
                                    placeholder="Enter URL"
                                    onChange={this.handleMainPhotoChange}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>HTML Text</ControlLabel>
                                <FormControl
                                    componentClass="textarea"
                                    rows={20}
                                    value={this.state.newsPost.htmlText ? this.state.newsPost.htmlText : ""}
                                    placeholder="Enter Text"
                                    onChange={this.handleHtmlTextChange}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Checkbox
                                    onChange={this.handleVisibleInSearchChange}
                                    checked={!!this.state.newsPost.visibleInSearchEngines}
                                >Visible in search</Checkbox>
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Tags</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.tags ? this.state.newsPost.tags : ""}
                                    placeholder="Enter Tags. Comma separated"
                                    onChange={this.handleTagsChange}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Visibility</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.visibility ? this.state.newsPost.visibility : ""}
                                    placeholder="Enter visibility"
                                    onChange={this.handleVisibilityChange}
                                />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Comments Rights</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.commentsRights ? this.state.newsPost.commentsRights : ""}
                                    placeholder="Enter comment rights"
                                    onChange={this.handleCommentsRightsChange}
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