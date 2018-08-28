import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import * as Actions from '../actions/BookListActions'
import { bindActionCreators } from 'redux'
import { BookListItem } from '../components/BookListItem'


class BookList extends Component {

  componentWillMount() {
    this.props.actions.loadBookList()
  }

  render() {

    const {items} = this.props || []

    return (
      <div>
        <div className="list-group">
          {items.forEach(i => <BookListItem {...i} />)}
        </div>
        <div className="card">
          <div className="card-body">
            <a href="#" className="btn btn-primary">Зарегистрировать новую книгу</a>
          </div>
        </div>
      </div>
    )
  }
}

BookList.propTypes = {
  items: PropTypes.arrayOf(PropTypes.object).isRequired
}

const mapStateToProps = (state) => {
  return {
    state: state.bookList
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(BookList)
