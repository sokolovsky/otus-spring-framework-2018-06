import React, { Component } from 'react'
import PropTypes from 'prop-types'

export class BookCommentItem extends Component {
  render() {
    const { text, time } = this.props
    return (
      <li className="media mt-3">
        <div className="media-body">
          <h5 className="mt-0 mb-1">{time}</h5>
          <span>{text}</span>
        </div>
      </li>
    )
  }
}

BookCommentItem.propTypes = {
  text: PropTypes.string.isRequired,
  time: PropTypes.string.isRequired,
}
