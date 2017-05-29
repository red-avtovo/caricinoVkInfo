'use strict';

const React = require('react');
const {Modal, Button, Col, Thumbnail, Label, Collapse, ButtonToolbar, FormGroup, ControlLabel, FormControl, Checkbox} = require('react-bootstrap');
const client = require('../client');
import update from "react-addons-update";

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