'use strict';

import React from "react";
import client from "../client";
import EmptyPostModal from "./emptyPostModal"

class PostModal extends EmptyPostModal {
    constructor(props) {
        super(props);
        this.state = {
            opened: props.opened !== 'undefined' ? props.opened : true,
            post: props.post,
            newsPost: {}
        };
    }

    componentDidMount() {
        client({
            method: 'POST',
            path: '/posts/create',
            entity: this.state.post,
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .done(response => {
                this.setState({
                    newsPost: response.entity,
                });
            });

    }
}

export default PostModal;