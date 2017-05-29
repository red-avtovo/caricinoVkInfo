'use strict';

import React from "react";
import PostTable from "./postTable";
import FilterBar from "./filterBar";

class FilterablePostTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showIgnored: props.showIgnored || false
        };
    }

    render() {
        return (
            <div>
                <FilterBar
                    showIgnored={this.state.showIgnored}
                />
                <PostTable
                    showIgnored={this.state.showIgnored}
                />
            </div>
        );
    }
}

export default FilterablePostTable;