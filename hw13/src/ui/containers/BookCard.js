import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import * as Actions from '../actions/BookCardActions'
import { connect } from 'react-redux'
import classNames  from 'classnames'
import { NavLink } from 'react-router-dom'
import { Dictionary } from '../utils/dictionary'
import BookComments from './BookComments'

class BookCard extends Component {
  getId() {
    return this.props.match.params.id
  }

  componentWillMount() {
    this.props.actions.loadBookCard(this.getId())
  }

  componentWillReceiveProps(nextProps) {
    console.log('will receive props', nextProps);
    const bookId = this.getId();
    if (nextProps.canEdit === 'LOAD') {
      this.props.actions.loadCanEditBook(bookId);
    }

    if (nextProps.canDelete === 'LOAD') {
      this.props.actions.loadCanDeleteBook(bookId);
    }
  }

  onDeleteClick(e) {
    e.preventDefault();
    this.props.actions.deleteBook(this.getId());
    this.props.actions.goBack()
  }

  render() {
    const {authors, title, genres, isbn} = this.props.book;
    let {canEdit, canDelete} = this.props;
    if (canEdit === 'LOAD' || canEdit === 'LOADING') {
      canEdit = false;
    }
    if (canDelete === 'LOAD' || canDelete === 'LOADING') {
      canDelete = false;
    }

    return (
      <div>
        <div className="card">
          <div className="card-header">{Dictionary.of(authors).getValues().join(', ') + ' / ' + isbn}</div>
          <div className="card-body">
            <h5 className="card-title">{title}</h5>
            <p className="card-text">{Dictionary.of(genres).getValues().join(", ")}</p>
            {canEdit && <div><NavLink to={"/book/edit/" + this.getId()}
                             className={classNames('btn', 'btn-primary')}>Редактировать</NavLink>
            {canDelete && <a href="#" className={classNames('btn', 'btn-danger')}
                             style={{marginLeft: '10px'}} onClick={this.onDeleteClick.bind(this)}>Удалить из библиотеки</a>}
            </div>
            }
          </div>
        </div>
        <BookComments bookId={this.getId()}/>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(state => {return {...state.bookCard}}, mapDispatchToProps)(BookCard)
