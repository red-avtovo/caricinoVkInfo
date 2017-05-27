'use strict';

const React = require('react');
const {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl} = require('react-bootstrap');
const client = require('../client');

class PostBox extends React.Component {

    constructor(props) {
        super(props);
        this.state = {post: props.post, modal: false, fullText: false, newsPost: {}};
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.toggleFullText = this.toggleFullText.bind(this);
        this.handleTitleChange = this.handleTitleChange.bind(this);
    }

    openModal() {
        client({
            method: 'POST',
            path: '/posts/create',
            entity: this.state.post,
            headers: {
                'Content-Type': 'application/json'
            }
        }).done(response => {
            this.setState({newsPost: response.entity})
            this.setState({modal: true})
        });
    }

    closeModal() {
        this.setState({modal: false})
    }

    toggleFullText() {
        this.setState({fullText: !this.state.fullText})
    }

    handleTitleChange(e) {
        //alert(e.target.value);
        //this.state.newsPost.title = e.target.value;
        return true;
    }

    render() {
        return (
            <div>
                <Col xs={6} md={4}>
                    <Thumbnail className="postCard">
                        <ButtonToolbar>
                            <Button bsStyle="danger" bsSize="small" className="pull-right">Ignore</Button>
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

                <Modal show={this.state.modal} onHide={this.closeModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <h4>Create news post</h4>
                        <hr />
                        <form>
                            <FormGroup>
                                <ControlLabel>Title</ControlLabel>
                                <FormControl
                                    type="text"
                                    value={this.state.newsPost.title}
                                    placeholder="Enter Title"
                                    onChange={this.handleTitleChange}
                                />
                            </FormGroup>
                        </form>
                        <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula.</p>


                        <h4>Overflowing text to show scroll behavior</h4>
                        <p>Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in,
                            egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
                        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus
                            vel augue laoreet rutrum faucibus dolor auctor.</p>
                        <p>Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque
                            nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor
                            fringilla.</p>
                        <p>Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in,
                            egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
                        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus
                            vel augue laoreet rutrum faucibus dolor auctor.</p>
                        <p>Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque
                            nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor
                            fringilla.</p>
                        <p>Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in,
                            egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
                        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus
                            vel augue laoreet rutrum faucibus dolor auctor.</p>
                        <p>Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque
                            nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor
                            fringilla.</p>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.closeModal}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}

export default PostBox;