'use strict';
import React from "react";
import PropTypes from "prop-types";
import {OpenGraphObject} from "./types";

const GraphLink = ({link, onClick}) => (
    <span>
        -<a href={link.href} onClick={(e) => { e.preventDefault(); onClick(link.index);}}>{link.index + 1}</a>-&nbsp;
    </span>
);

GraphLink.PropTypes = {
    link: PropTypes.instanceOf(OpenGraphObject),
    onClick: PropTypes.func
};

export default GraphLink;