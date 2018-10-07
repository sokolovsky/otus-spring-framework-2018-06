import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import React, { Component } from 'react'
import { BookCommentItem } from '../components/BookCommentItem'
import * as Actions from '../actions/BookCommentsActions'
import PropTypes from 'prop-types'

class BookComments extends Component {
  constructor(props) {
    super(props)

    this.textRef = React.createRef()
  }

  onSendClick(e) {
    e.preventDefault()
    const bookId = this.props.bookId
    this.props.actions.sendComment(bookId, this.currentCommentText())
    this.clearCommentArea()
  }

  currentCommentText() {
    return this.textRef.current.value
  }

  clearCommentArea() {
    this.textRef.current.value = ''
  }

  componentWillMount() {
    const bookId = this.props.bookId
    this.props.actions.loadBookComments(bookId)
  }

  componentWillReceiveProps(nextProps) {
    const bookId = this.props.bookId
    if (nextProps.load) {
      this.props.actions.loadBookComments(bookId)
    }
  }

  render() {
    const { items, canComment } = this.props
    let i = 0
    return <div>
      <div className="card">
        <div className="card-header">
          Комментарии
        </div>
        <div className="card-body">
          <ul className="list-unstyled">
            {
              items.map((c) => {
              return <BookCommentItem text={c.text} time={c.time} key={i++} />
            })}
          </ul>
        </div>
      </div>
      {canComment && <div className="card">
        <div className="card-body">
          <form>
            <div className="form-group">
              <label htmlFor="text">Текст комментария</label>
              <textarea className="form-control" id="text" name="text" rows="3" ref={this.textRef}/>
            </div>
            <button type="submit" className="btn btn-primary mb-2" onClick={this.onSendClick.bind(this)}>Отправить
              комментарий
            </button>
          </form>
        </div>
      </div>}
    </div>
  }
}

BookComments.propTypes = {
  bookId: PropTypes.string.isRequired,
  items: PropTypes.array.isRequired
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(state => {return {...state.bookComments}}, mapDispatchToProps)(BookComments)
