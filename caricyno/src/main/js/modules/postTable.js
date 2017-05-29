'use strict';

const React = require('react');
const {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl, Checkbox} = require('react-bootstrap');
const client = require('../client');
import PostBox from "./postBox.js";

class PostTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            postsPromise: props.postsPromise,
            showIgnored: false
        };
    }

    render() {
        var rows = [];
        this.state.postsPromise.then(function (response) {
            var posts = response.entity;
            posts.forEach((post) => {
                if (post.isIgnored && !this.props.showIgnored) {
                    return;
                }
                rows.push(<PostBox post={post}/>);
            });
        });
        return (
            <table>
                <tbody>{rows}</tbody>
            </table>
        );
    }
}
export default PostTable;