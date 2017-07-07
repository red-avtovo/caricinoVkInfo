'use strict';
import React from "react";
import GraphLink from "./graphLink";
import PropTypes from "prop-types";
import {OpenGraphObject} from "./types";
import {ControlLabel, FormGroup} from "react-bootstrap";

const GraphLinksList = ({links, onClick}) => {
    if (links && links.length > 0) {
        return (
            <FormGroup>
                <ControlLabel>Load From:&nbsp;</ControlLabel>
                {links.map(link => (<GraphLink key={link.index} link={link} onClick={onClick} />))}
            </FormGroup>
        );
    }
    return null;
};

GraphLinksList.PropTypes = {
    links: PropTypes.arrayOf(OpenGraphObject),
    onClick: PropTypes.func
};

export default GraphLinksList;