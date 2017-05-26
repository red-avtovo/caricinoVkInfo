'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
import PostBox from './modules/postBox.js';

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state={posts: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/posts?count=5'}).done(response => {
            this.setState({posts: response.entity});
        });
    }

    render() {
        return (
            <div>
                {this.state.posts.map(
                    item => <PostBox post={item} key={item.id}/>
                )}
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('root')
);