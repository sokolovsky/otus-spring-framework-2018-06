import { Component } from 'react'
import React from 'react'
import PropTypes from 'prop-types'
import classNames from 'classnames'


export class Menu extends Component {
  render() {
    const { items, active } = this.props
    console.log(items)
    return <ul className="nav">
      {Object.keys(items).map(url => {
        const label = items[url]
        return <li className="nav-item" key={url}>
          <a className={classNames('nav-link', {'active': active === url})} href={url}>{label}</a>
        </li>
      })}
    </ul>
  }
}

Menu.propTypes = {
  items: PropTypes.objectOf(PropTypes.string).isRequired,
  active: PropTypes.string
}
