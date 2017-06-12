'use strict';

import React from "react";
import PostBox from "./postBox";
import client from '../client';

class PostTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            posts: [],
            showIgnored: props.showIgnored || false,
            showPosted: props.showPosted || false
        };
        this.handleIgnoredRecord = this.handleIgnoredRecord.bind(this);
        this.handlePostedRecord = this.handlePostedRecord.bind(this);
    }

    handleIgnoredRecord(ignoredRecord) {
        this.props.onIgnoredRecord(ignoredRecord);
    }

    handlePostedRecord() {
        this.props.onPostedRecord();
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
            if ((post.isIgnored && !this.props.showIgnored)
                || (post.isPosted && !this.props.showPosted)) {
                return;
            }
            elements.push(
                <PostBox
                    key={post.id} post={post}
                    onIgnoredChange={this.handleIgnoredRecord}
                    onPostedRecord={this.handlePostedRecord}
                />);
        });

        return (
            <div>
                {elements}
            </div>
        );
    }
}
export default PostTable;