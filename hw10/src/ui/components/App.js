import React from 'react'

const Header = (props) => (
    <div>
        <h1>{props.title}</h1>
    </div>
);

export default class App extends React.Component {

    constructor() {
        super();
        this.state = {text: ''};
    }

    componentDidMount() {
        fetch('/text')
            .then(res => res.json())
            .then(json => this.setState({
                text: json.text
            }))
    }

    render() {
        return <div>
            <Header title="ШАПКА САЙТА"/>
            <h3>{this.state.text}</h3>
        </div>
    }
};
