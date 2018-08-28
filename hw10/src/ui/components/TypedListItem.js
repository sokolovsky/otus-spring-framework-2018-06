import React, { Component } from 'react'
import PropTypes  from 'prop-types'


export class TypedListItem extends Component {
  render() {
    const { name, count, detailUrl } = this.props
    return <li className="list-group-item d-flex justify-content-between align-items-center">
      <a href="/authors/id">{name}</a>
      <span className="badge badge-primary badge-pill">{count}</span>
    </li>
  }
}

TypedListItem.propTypes = {
  id: PropTypes.any.isRequired,
  name: PropTypes.string.isRequired,
  count: PropTypes.number.isRequired,
  detailUtl: PropTypes.string
}
