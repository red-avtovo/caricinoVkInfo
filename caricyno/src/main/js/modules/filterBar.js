'use strict';

import React from "react";
import {Checkbox} from "react-bootstrap";

class FilterBar extends React.Component {
    constructor(props) {
        super(props);
        this.handleIgnoredInputChange = this.handleIgnoredInputChange.bind(this);
    }

    handleIgnoredInputChange(e) {
        this.props.onIgnoredInputChange(e.target.checked);
    }

    render() {
        return (
            <Checkbox
                checked={this.props.showIgnored}
                onChange={this.handleIgnoredInputChange}
            >Show ignored</Checkbox>
        );
    }

}
export default FilterBar;