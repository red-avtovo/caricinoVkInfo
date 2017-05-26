'use strict';

const React = require('react');

class PostBox extends React.Component {


    render() {
        const post = this.props.post;
        return (
            <div className='card'>
                <div className='card-block'>
                    <h4 className='card-title'>{post.title}</h4>
                    <span className='badge badge-default badge-pill badge-info'>{post.author}</span>
                    <p className='card-text'>
                        {post.text}
                        <a href='#' className='btn btn-primary btn-sm'>Create news post</a>
                    </p>
                </div>
            </div>
        )
    }
}

export default PostBox;