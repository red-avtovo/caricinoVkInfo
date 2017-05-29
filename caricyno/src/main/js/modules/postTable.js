'use strict';

import React from "react";
import PostBox from "./postBox";
import client from '../client';

class PostTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            posts: [],
            showIgnored: props.showIgnored || false
        };
    }

    componentDidMount() {
        client({method: 'GET', path: '/posts?count=5'})
            .done(response => {
                this.setState({posts: response.entity});
            });
    }

    render() {
        let elements = [];

        this.state.posts.forEach((post) => {
            if (post.isIgnored && !this.props.showIgnored) {
                return;
            }
            elements.push(<PostBox post={post}/>);
        });

        return (
            <div>
                {elements}
            </div>
        );
    }
}
export default PostTable;