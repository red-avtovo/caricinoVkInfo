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
            showPosted: props.showPosted || false
        };
        this.handleIgnoredInput = this.handleIgnoredInput.bind(this);
        this.handleIgnored = this.handleIgnored.bind(this);
        this.handlePostedInput = this.handlePostedInput.bind(this);
        this.handlePosted = this.handlePosted.bind(this);
    }

    handleIgnoredInput(showIgnored) {
        this.setState({
            showIgnored: showIgnored
        })
    }

    handlePostedInput(showPosted) {
        this.setState({
            showPosted: showPosted
        })
    }

    handleIgnored(ignored) {
        this.setState({
            ignored: ignored
        })
    }

    handlePosted(posted) {
        this.setState({
            posted: posted
        })
    }

    render() {
        return (
            <div>
                <FilterBar
                    showIgnored={this.state.showIgnored}
                    showPosted={this.state.showPosted}
                    onIgnoredInputChange={this.handleIgnoredInput}
                    onPostedInputChange={this.handlePostedInput}
                />
                <Button bsStyle="primary" bsSize="small" onClick={() => this.refs.modal.open()}>Create news
                    post</Button>
                <p/>
                <EmptyPostModal ref="modal"/>
                <PostTable
                    key="postTable"
                    showIgnored={this.state.showIgnored}
                    showPosted={this.state.showPosted}
                    onIgnoredRecord={this.handleIgnored}
                    onPostedRecord={this.handlePosted}
                />
            </div>
        );
    }
}

export default FilterablePostTable;