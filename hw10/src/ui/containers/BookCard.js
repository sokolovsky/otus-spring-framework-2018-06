import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import * as Actions from '../actions/BookCardActions'
import { connect } from 'react-redux'
import classNames  from 'classnames'
import { NavLink } from 'react-router-dom'


class BookCard extends Component {
  getId() {
    return this.props.match.params.id
  }

  componentWillMount() {
    this.props.actions.loadBookCard(this.getId())
  }


  onDeleteClick(e) {
    e.preventDefault()
    this.props.actions.deleteBook(this.getId())
    this.props.actions.goBack()
  }

  render() {
    const {author, title, genres, isbn} = this.props.book
    return (
      <div className="card">
        <div className="card-header">{author + ' / ' + isbn}</div>
        <div className="card-body">
          <h5 className="card-title">{title}</h5>
          <p className="card-text">{genres.join(", ")}</p>
          <NavLink to={"/book/edit/" + this.getId()} className={classNames('btn', 'btn-primary')}>Редактировать</NavLink>
          <a href="#" className={classNames('btn', 'btn-danger')} style={{marginLeft: '10px'}} onClick={this.onDeleteClick.bind(this)}>Удалить из библиотеки</a>
        </div>
      </div>
    )
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(state => {return {...state.bookCard}}, mapDispatchToProps)(BookCard)
