'use strict';

import React from "react";
import {Checkbox} from "react-bootstrap";

class FilterBar extends React.Component {
    constructor(props) {
        super(props);
        this.handleIgnoredInputChange = this.handleIgnoredInputChange.bind(this);
        this.handlePostedInputChange = this.handlePostedInputChange.bind(this);
    }

    handleIgnoredInputChange(e) {
        this.props.onIgnoredInputChange(e.target.checked);
    }

    handlePostedInputChange(e) {
        this.props.onPostedInputChange(e.target.checked);
    }

    render() {
        return (
            <div>
                <Checkbox
                    checked={this.props.showIgnored}
                    onChange={this.handleIgnoredInputChange}
                >Show ignored</Checkbox>
                <Checkbox
                    checked={this.props.showPosted}
                    onChange={this.handlePostedInputChange}
                >Show posted</Checkbox>
            </div>
        );
    }

}
export default FilterBar;