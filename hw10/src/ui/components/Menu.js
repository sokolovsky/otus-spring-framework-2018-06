import { Component } from 'react'
import React from 'react'
import PropTypes from 'prop-types'
import classNames from 'classnames'
import { Link } from 'react-router-dom'
import { routes } from '../routes'

export class Menu extends Component {
  render() {
    const { items, active } = this.props
    return <div>
    <ul className="nav">
      {Object.keys(items).map(url => {
        const label = items[url]
        return <li className="nav-item" key={url}>
          <div className={classNames('nav-link', {'active': active === url})}>
            <Link to={url}>
              {label}
            </Link>
          </div>
        </li>
      })}
    </ul>
    {routes}
    </div>
  }
}

Menu.propTypes = {
  items: PropTypes.objectOf(PropTypes.string).isRequired,
  active: PropTypes.string
}
