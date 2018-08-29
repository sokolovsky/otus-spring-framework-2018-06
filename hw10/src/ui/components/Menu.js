import { Component } from 'react'
import React from 'react'
import PropTypes from 'prop-types'
import classNames from 'classnames'
import { NavLink } from 'react-router-dom'
import { routes } from '../routes'

export class Menu extends Component {
  onChangeActive (url) {
    this.setState({active: url})
  }

  constructor(props) {
    super(props)
    this.state = {...props}
  }

  render() {
    const { items, active } = this.state

    return <div>
    <ul className="nav nav-pills" style={{margin: '20px 0'}}>
      {Object.keys(items).map(url => {
        const label = items[url]
        return <li className="nav-item" key={url}>
          <div>
            <NavLink to={url} onClick={this.onChangeActive.bind(this, url)} activeClassName="" className={classNames('nav-link', {'active': active === url})}>
              {label}
            </NavLink>
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
