'use strict';

import React from "react";
import client from "../client";
import NewsModal from "./newsModal";

class EmptyPostModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.open =this.open.bind(this);
        this.close =this.close.bind(this);
        this.handleModalPostedChange =this.handleModalPostedChange.bind(this);
    }

    open() {
        client({
            method: 'GET',
            path: '/posts/new',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .done(response => {
            this.refs.modal.open(response.entity);
        });
    }

    close() {
        this.refs.modal.close();
    }

    handleModalPostedChange() {
        this.props.onModalPosted();
    }

    render() {
        return (
            <NewsModal ref="modal" onNewsModalPosted = {this.handleModalPostedChange} />
        )
    }
}

export default EmptyPostModal;