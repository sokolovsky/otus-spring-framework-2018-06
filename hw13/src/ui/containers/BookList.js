import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import * as Actions from '../actions/BookListActions'
import { bindActionCreators } from 'redux'
import { BookListItem } from '../components/BookListItem'
import { NavLink } from 'react-router-dom'


class BookList extends Component {

  getFilter() {
    return {
      'genre': this.props.match.params.genre || null,
      'author': this.props.match.params.author || null,
    }
  }

  componentWillMount() {
    if (this.props.change) {
      this.props.actions.loadBookList(this.getFilter())
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.canAdd === 'LOAD') {
      this.props.actions.loadCanAdd()
    }
  }

  render() {
    const {items} = this.props;
    let {canAdd} = this.props;

    if (canAdd === 'LOAD' || canAdd === 'LOADING') {
      canAdd = false;
    }

    return (
      <div>
        <div className="list-group">
          {items.map(i => {
            return <BookListItem {...i} key={i.isbn} />
          })}
        </div>
        {canAdd && <div className="card">
          <div className="card-body">
            <NavLink to="/book/add/" className="btn btn-primary">Зарегистрировать новую книгу</NavLink>
          </div>
        </div>}
      </div>
    );
  }
}

BookList.propTypes = {
  items: PropTypes.arrayOf(PropTypes.object).isRequired,
  change: PropTypes.bool.isRequired
};

const mapStateToProps = (state) => {
  return {...state.bookList}
};

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(BookList)
