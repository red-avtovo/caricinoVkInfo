'use strict';

import React from 'react';
import {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl, Checkbox} from 'react-bootstrap';
import client from '../client';

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