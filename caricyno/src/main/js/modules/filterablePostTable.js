'use strict';

import React from "react";
import PostTable from "./postTable";
import FilterBar from "./filterBar";
import EmptyPostModal from "./emptyPostModal";
import {Button} from "react-bootstrap";

class FilterablePostTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showIgnored: props.showIgnored || false,
            showPublished: props.showPublished || false
        };
        this.handleIgnoredInput = this.handleIgnoredInput.bind(this);
        this.handleIgnored = this.handleIgnored.bind(this);
        this.handlePublishedInput = this.handlePublishedInput.bind(this);
        this.handlePublished = this.handlePublished.bind(this);
    }

    handleIgnoredInput(showIgnored) {
        this.setState({
            showIgnored: showIgnored
        })
    }

    handlePublishedInput(showPublished) {
        this.setState({
            showPublished: showPublished
        })
    }

    handleIgnored(ignored) {
        this.setState({
            ignored: ignored
        })
    }

    handlePublished(published) {
        this.setState({
            published: published
        })
    }

    render() {
        return (
            <div>
                <FilterBar
                    showIgnored={this.state.showIgnored}
                    showPublished={this.state.showPublished}
                    onIgnoredInputChange={this.handleIgnoredInput}
                    onPublishedInputChange={this.handlePublishedInput}
                />
                <Button bsStyle="primary" bsSize="small" onClick={() => this.refs.modal.open()}>Create news
                    post</Button>
                <p/>
                <EmptyPostModal ref="modal"/>
                <PostTable
                    key="postTable"
                    showIgnored={this.state.showIgnored}
                    showPublished={this.state.showPublished}
                    onIgnoredRecord={this.handleIgnored}
                    onPublishedRecord={this.handlePublished}
                />
            </div>
        );
    }
}

export default FilterablePostTable;