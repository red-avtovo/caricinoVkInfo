'use strict';

import React from "react";
import client from "../client";
import NewsModal from "./newsModule";

class EmptyPostModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            opened: props.opened !== 'undefined' ? props.opened : true,
            newsPost: {}
        };
    }

    componentDidMount() {
        client({
            method: 'GET',
            path: '/posts/new',
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

    render() {
        return (
            <NewsModal newsPost={this.state.newsPost} opened={this.state.opened}/>
        )
    }
}

export default EmptyPostModal;