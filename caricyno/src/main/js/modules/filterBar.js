'use strict';

import React from "react";

class FilterBar extends React.Component {
    render() {
        return (
            <form>
                <p>
                    <input type="checkbox" checked={this.props.showIgnored}/>
                    Show ignored
                </p>
            </form>
        );
    }

}
export default FilterBar;