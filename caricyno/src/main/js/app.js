'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import FilterablePostTable from "./modules/filterablePostTable";

class App extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <FilterablePostTable/>
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('root')
);