'use strict';

import React from "react";
import client from "../client";
import EmptyPostModal from "./emptyPostModal"

class PostModal extends EmptyPostModal {
    constructor(props) {
        super(props);
        this.state = {};
        this.open =this.open.bind(this);
    }

    open(post) {
        client({
            method: 'POST',
            path: '/posts/create',
            entity: post,
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .done(response => {
                this.refs.modal.open(response.entity);
            });
    }
}

export default PostModal;