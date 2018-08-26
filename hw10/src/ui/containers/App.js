import React, { Component } from 'react'
// import { browserHistory, Router } from 'react-router'
// import { routes } from '../routes'
import { Header } from '../components/Header'
import { Menu } from '../components/Menu'
// import PropTypes from 'prop-types'

export default class App extends React.Component {

    componentDidMount() {
        fetch('/text')
            .then(res => res.json())
            .then(json => this.setState({
                text: json.text
            }))
    }

    render() {
        return <div className="container-fluid">
          <Header title={"Библиотека книг"}/>
          <Menu items={{
            '/': 'Книги',
            '/authors/': 'Авторы',
            '/genres/': 'Жанры'
          }} active={'/'}/>
          {/*<Router*/}
            {/*history={browserHistory}*/}
            {/*routes={routes}*/}
          {/*/>*/}
        </div>
    }
};
