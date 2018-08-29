import {
  ACTION_BOOK_CARD_LOAD_START,
  ACTION_BOOK_CARD_LOADED
} from '../constants'

const initialState = {
  book: {
    id: '',
    authors: [],
    title: '',
    isbn: '',
    genres: [],
  },
}

export function bookCardReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_BOOK_CARD_LOADED:
      return {...state, ...action.payload}
    case ACTION_BOOK_CARD_LOAD_START:
    default:
      return state
  }
}
