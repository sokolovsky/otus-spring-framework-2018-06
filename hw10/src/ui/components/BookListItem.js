import React, { Component } from 'react'


export class BookListItem extends Component {
  render() {
    const {author, isbn, title, genres} = this.props
    return (
      <a href="#" className="list-group-item list-group-item-action flex-column align-items-start">
        <div className="d-flex w-100 justify-content-between">
          <h5 className="mb-1">{author}</h5>
          <small className="text-muted">{isbn}</small>
        </div>
        <p className="mb-1">{title}</p>
        <small className="text-muted">{genres.join(', ')}</small>
      </a>
    )
  }
}