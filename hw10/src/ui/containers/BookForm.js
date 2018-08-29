import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { FormField } from '../components/FormField'
import { SelectFormField } from '../components/SelectFormField'
import * as Actions from '../actions/BookFormActions'
import { Dictionary } from '../utils/dictionary'

class BookForm extends Component {

  getId() {
    return this.props.match.params.id
  }

  componentWillMount() {
    this.props.actions.loadGenresDictionary()
    this.props.actions.loadAuthorsDictionary()

    const id = this.getId()
    if (id) {
      this.props.actions.loadBookFields(id)
    }
  }

  onSave(e) {
    e.preventDefault()
    console.log("onSave")
    // give fields
    // send
  }

  render() {
    const { isbn, title } = this.props.book
    const authors = Dictionary.of(this.props.book.authors).getKeys()
    const genres = Dictionary.of(this.props.book.genres).getKeys()

    const dictionary = {
      authors: this.props.authors,
      genres: this.props.genres
    }

    return (
      <div className="card">
        <div className="card-header">{Dictionary.of(this.props.book.authors).getValues().join(', ')}</div>
        <div className="card-body">
          <form action="/">
            <FormField label="ISBN" forId="isbn">
              <input type="text" className="form-control"
                     id="isbn"
                     name="isbn"
                     placeholder="99348602-0348643" value={isbn} />
            </FormField>
            <FormField label="Автор(ы)" forId="authors">
              <SelectFormField name="authors" items={dictionary.authors} multiple={true} value={authors}/>
            </FormField>
            <FormField label="Жанр(ы)" forId="genres">
              <SelectFormField name="genres" items={dictionary.genres} multiple={true} value={genres}/>
            </FormField>
            <FormField label="Название книги" forId="title">
              <input id="title" name="title" className="form-control" type="text"
                     placeholder="Вечера на хуторе близ диканьки" value={title}/>
            </FormField>
            <button type="submit" className="btn btn-primary" onClick={this.onSave.bind(this)}>Сохранить</button>
          </form>
        </div>
      </div>
    )
  }
}

BookForm.propTypes = {
  book: PropTypes.shape({
    isbn: PropTypes.string,
    title: PropTypes.string,
    authors: PropTypes.object.isRequired,
    genres: PropTypes.object.isRequired
  }).isRequired,
  authors: PropTypes.object.isRequired,
  genres: PropTypes.object.isRequired,
}

const mapStateToProps = (state) => {
  return {...state.bookForm}
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(BookForm)
