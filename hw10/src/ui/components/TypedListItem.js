import React, { Component } from 'react'
import PropTypes  from 'prop-types'
import { Link } from 'react-router-dom'

export class TypedListItem extends Component {
  render() {
    const { name, title, count, detailUrl } = this.props
    return <li className="list-group-item d-flex justify-content-between align-items-center">
      <Link to={detailUrl}>{name || title}</Link>
      <span className="badge badge-primary badge-pill">{count}</span>
    </li>
  }
}

TypedListItem.propTypes = {
  id: PropTypes.any.isRequired,
  name: PropTypes.string,
  title: PropTypes.string,
  count: PropTypes.number.isRequired,
  detailUrl: PropTypes.string
}
