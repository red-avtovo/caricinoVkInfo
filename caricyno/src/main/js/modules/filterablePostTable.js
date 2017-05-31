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
            showIgnored: props.showIgnored || false
        };
        this.handleIgnoredInput = this.handleIgnoredInput.bind(this);
        this.handleIgnored = this.handleIgnored.bind(this);
    }

    handleIgnoredInput(showIgnored) {
        this.setState({
            showIgnored: showIgnored
        })
    }

    handleIgnored(ignored) {
        this.setState({
            ignored: ignored
        })
    }

    render() {
        return (
            <div>
                <FilterBar
                    showIgnored={this.state.showIgnored}
                    onIgnoredInputChange={this.handleIgnoredInput}
                />
                <Button bsStyle="primary" bsSize="small" onClick={() => this.refs.modal.open()}>Create news
                    post</Button>
                <p/>
                <EmptyPostModal ref="modal"/>
                <PostTable
                    key="postTable"
                    showIgnored={this.state.showIgnored}
                    onIgnoredRecord={this.handleIgnored}
                />
            </div>
        );
    }
}

export default FilterablePostTable;