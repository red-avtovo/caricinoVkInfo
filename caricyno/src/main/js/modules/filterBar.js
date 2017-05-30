'use strict';

import React from "react";
import {Checkbox} from "react-bootstrap";

class FilterBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {showIgnored: props.showIgnored};
    }

    render() {
        return (
            <Checkbox
                checked={this.state.showIgnored}
                onChange={(e) => this.setState({showIgnored: e.target.checked})}
            >Show ignored</Checkbox>
        );
    }

}
export default FilterBar;