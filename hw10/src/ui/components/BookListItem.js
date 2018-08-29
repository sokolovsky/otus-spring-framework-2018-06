import React, { Component } from 'react'
import { NavLink } from 'react-router-dom'
import PropTypes from 'prop-types'


export class BookListItem extends Component {
  render() {
    const {author, isbn, title, genres, id} = this.props
    return (
      <NavLink to={"/book/card/" + id} className="list-group-item list-group-item-action flex-column align-items-start">
        <div className="d-flex w-100 justify-content-between">
          <h5 className="mb-1">{author}</h5>
          <small className="text-muted">{isbn}</small>
        </div>
        <p className="mb-1">{title}</p>
        <small className="text-muted">{genres.join(', ')}</small>
      </NavLink>
    )
  }
}

BookListItem.propTypes = {
  author: PropTypes.string.isRequired,
  isbn: PropTypes.string.isRequired,
  genres: PropTypes.array,
  id: PropTypes.string.isRequired
}
