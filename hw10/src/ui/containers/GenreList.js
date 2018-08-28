import React, { Component } from 'react'
import { TypedListItem } from '../components/TypedListItem'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { bindActionCreators } from 'redux'
import * as Actions from '../actions/GenreListActions'


class GenreList extends Component {

  componentWillMount() {
    this.props.actions.loadGenreList()
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

GenreList.propTypes = {
  items: PropTypes.arrayOf(PropTypes.object).isRequired
}

const mapStateToProps = (state) => {
  return {...state.genreList}
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(GenreList)
