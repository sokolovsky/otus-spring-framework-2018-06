import { Component } from 'react'
import React from 'react'
import PropTypes from 'prop-types'

export class Header extends Component {
  render() {
    const title = this.props.title
    return <nav className="navbar navbar-light bg-light">
      <span className="navbar-text">
          <h1>{title}</h1>
      </span>
    </nav>
  }
}

Header.propTypes = {
  title: PropTypes.string.isRequired
}
