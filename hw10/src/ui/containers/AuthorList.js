import React, { Component } from 'react'
import { TypedListItem } from '../components/TypedListItem'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { bindActionCreators } from 'redux'
import * as Actions from '../actions/AuthorListActions'


class AuthorList extends Component {

  componentWillMount() {
    this.props.actions.loadAuthorList()
  }

  render() {
    const { items } = this.props
    return (
      <ul className="list-group">
        {items.map(i => <TypedListItem {...i} count={i.bookCount} key={i.id}/>)}
      </ul>
    )
  }
}

AuthorList.propTypes = {
  items: PropTypes.arrayOf(PropTypes.object).isRequired
}

const mapStateToProps = (state) => {
  return {...state.authorList}
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(AuthorList)
