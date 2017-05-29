'use strict';

const React = require('react');
const {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl, Checkbox} = require('react-bootstrap');
const client = require('../client');
import PostTable from "./postTable.js";
import FilterBar from "./filterBar.js";

class FilterablePostTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            postsPromise: props.postsPromise,
            showIgnored: false
        };
    }

    render() {
        return (
            <div>
                <FilterBar
                    showIgnored={this.state.showIgnored}
                />
                <PostTable
                    posts={this.state.postsPromise}
                />
            </div>
        );
    }
}

export default FilterablePostTable;