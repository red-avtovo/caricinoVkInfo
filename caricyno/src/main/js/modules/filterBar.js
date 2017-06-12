'use strict';

import React from "react";
import {Checkbox} from "react-bootstrap";

class FilterBar extends React.Component {
    constructor(props) {
        super(props);
        this.handleIgnoredInputChange = this.handleIgnoredInputChange.bind(this);
        this.handlePublishedInputChange = this.handlePublishedInputChange.bind(this);
    }

    handleIgnoredInputChange(e) {
        this.props.onIgnoredInputChange(e.target.checked);
    }

    handlePublishedInputChange(e) {
        this.props.onPublishedInputChange(e.target.checked);
    }

    render() {
        return (
            <div>
                <Checkbox
                    checked={this.props.showIgnored}
                    onChange={this.handleIgnoredInputChange}
                >Show ignored</Checkbox>
                <Checkbox
                    checked={this.props.showPublished}
                    onChange={this.handlePublishedInputChange}
                >Show published</Checkbox>
            </div>
        );
    }

}
export default FilterBar;