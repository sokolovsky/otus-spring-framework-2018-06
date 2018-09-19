import {
  ACTION_BOOK_FORM_AUTHORS_LOADED,
  ACTION_BOOK_FORM_GENRES_LOADED,
  ACTION_BOOK_FORM_LOADED, ACTION_BOOK_FORM_SAVE_RESULT_RESPONSE,
} from '../constants'

const initialState = {
  book: {
    id: '',
    authors: {},
    title: '',
    isbn: '',
    genres: {},
  },
  genres: {},
  authors: {},
  saved: false,
  errors: {}
}

export function bookFormReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_BOOK_FORM_LOADED:
      return {...state, ...action.payload}
    case ACTION_BOOK_FORM_AUTHORS_LOADED:
      return {...state, ...{authors: action.payload}}
    case ACTION_BOOK_FORM_GENRES_LOADED:
      return {...state, ...{genres: action.payload}}
    case ACTION_BOOK_FORM_SAVE_RESULT_RESPONSE:
      return {...state, ...{saved: action.payload.success, errors: action.payload.errors || {}}}
    default:
      return state
  }
}
