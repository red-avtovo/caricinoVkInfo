'use strict';

const React = require('react');
const {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl, Checkbox} = require('react-bootstrap');
const client = require('../client');
import update from "react-addons-update";
import PostModal from "./postModal.js";

class PostBox extends React.Component {

    constructor(props) {
        super(props);
        this.state = {post: props.post, modal: false, successModal: false, fullText: false, newsPost: {}};
        this.ignoreRecord = this.ignoreRecord.bind(this);
        this.openModal = this.openModal.bind(this);
        this.openModalForNewPost = this.openModalForNewPost.bind(this);
        this.openModalForPost = this.openModalForPost.bind(this);
        this.toggleFullText = this.toggleFullText.bind(this);
    }

    ignoreRecord() {
        client({
            method: 'POST',
            path: '/posts/ignore',
            entity: this.state.post,
            headers: {
                'Content-Type': 'application/json'
            }
        }).done(response => {
            this.state.post.isIgnored = true;
        });
    }

    openModalForNewPost() {
        this.openModalForPost({
            method: 'GET',
            path: '/posts/new',
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    openModal() {
        this.openModalForPost({
            method: 'POST',
            path: '/posts/create',
            entity: this.state.post,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    openModalForPost(request) {
        client(request)
            .done(response => {
               return <PostModal
                    newsPost={response.entity}
                    modal={true}
                />
            });
    }

    toggleFullText() {
        this.setState({fullText: !this.state.fullText})
    }

    render() {
        return (
            <div>
                <Col xs={6} md={4}>
                    <Thumbnail className="postCard">
                        <ButtonToolbar>
                            <Button bsStyle="danger" bsSize="small" className="pull-right" onClick={this.ignoreRecord}>Ignore</Button>
                            <Button bsStyle="primary" bsSize="small" className="pull-right" onClick={this.openModal}>Create
                                news
                                post</Button>&nbsp;
                        </ButtonToolbar>
                        <div className="titleContainer">
                            <div className="ellipsis">
                                {this.state.post.title}
                            </div>
                        </div>
                        <p>
                            <Label bsStyle="info">{this.state.post.author}</Label>
                        </p>

                        <Button onClick={this.toggleFullText}>
                            Toggle full text
                        </Button>
                        <Collapse in={this.state.fullText}>
                            <div dangerouslySetInnerHTML={{__html: this.state.post.text}}>
                            </div>
                        </Collapse>
                    </Thumbnail>
                </Col>
            </div>
        )
    }
}

export default PostBox;