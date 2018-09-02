import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { FormField } from '../components/FormField'
import { SelectFormField } from '../components/SelectFormField'
import * as Actions from '../actions/BookFormActions'
import { Dictionary } from '../utils/dictionary'
import { SuccessText } from '../components/SuccessText'
import classNames from 'classnames'

class BookForm extends Component {

  constructor(props) {
    super(props)

    this.references = {
      isbn: React.createRef(),
      title: React.createRef(),
      authors: React.createRef(),
      genres: React.createRef(),
    }
  }

  getData() {
    return {
      'isbn': this.references.isbn.current.value,
      'title': this.references.title.current.value,
      'genres': this.references.genres.current.value(),
      'authors': this.references.authors.current.value()
    }
  }

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
    // e.preventDefault()
    // this.props.actions.saveBook({...this.getData(), ...{id: this.getId()}})
  }

  render() {
    const { isbn, title} = this.props.book
    const { saved, errors } = this.props
    const authors = Dictionary.of(this.props.book.authors).getKeys()
    const genres = Dictionary.of(this.props.book.genres).getKeys()

    const errorFields = Object.keys(errors)

    const dictionary = {
      authors: this.props.authors,
      genres: this.props.genres
    }

    return (
      <div className="card">
        <div className="card-header">
          {Dictionary.of(this.props.book.authors).getValues().join(', ')}
          <SuccessText success={saved} style={{ float: 'right' }} text="Данные успешно сохранены"/>
        </div>
        <div className="card-body">
          <form action="/">
            <FormField label="ISBN" forId="isbn">
              <input type="text" className={classNames('form-control', { 'is-invalid': errorFields.includes('isbn') })}
                     id="isbn"
                     name="isbn"
                     placeholder="99348602-0348643" defaultValue={isbn} ref={this.references.isbn}/>
            </FormField>
            <FormField label="Автор(ы)" forId="authors">
              <SelectFormField name="authors" items={dictionary.authors} multiple={true} value={authors}
                               className={classNames({'is-invalid': errorFields.includes('authors')})}
                               ref={this.references.authors}/>
            </FormField>
            <FormField label="Жанр(ы)" forId="genres">
              <SelectFormField name="genres" items={dictionary.genres} multiple={true} value={genres}
                               className={classNames({'is-invalid': errorFields.includes('genres')})}
                               ref={this.references.genres}/>
            </FormField>
            <FormField label="Название книги" forId="title">
              <input id="title" name="title" className={classNames("form-control", { 'is-invalid': errorFields.includes('title')})} type="text"
                     placeholder="Вечера на хуторе близ диканьки" defaultValue={title} ref={this.references.title}/>
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
