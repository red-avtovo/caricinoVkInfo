'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
import FilterablePostTable from "./modules/filterablePostTable";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {posts: []};
    }

    componentDidMount() {
        var postsPromise = new Promise(client({method: 'GET', path: '/posts?count=5'}));
        this.setState({postsPromise: postsPromise});
    }

    render() {
        return (
            <div>
                <FilterablePostTable posts={this.state.postsPromise}/>
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('root')
);