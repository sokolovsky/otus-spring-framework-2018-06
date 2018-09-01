import server from '../server'
import { history } from '../store/configureStore'
import {
  ACTION_BOOK_CARD_DELETED,
  ACTION_BOOK_CARD_LOAD_START,
  ACTION_BOOK_CARD_LOADED,
  BOOK_LIBRARY_UPDATE,
} from '../constants'

const loadingStart = () => {
  return {
    type: ACTION_BOOK_CARD_LOAD_START
  }
}

const deletingEnd = () => {
  return {
    type: ACTION_BOOK_CARD_DELETED
  }
}

const loadingEnd = (book) => {
  return {
    type: ACTION_BOOK_CARD_LOADED,
    payload: {book: book},
  }
}

export function loadBookCard(id) {
  return dispatch => {
    dispatch(loadingStart)
    server.getBook(id)
      .then(
        (book) => {
          dispatch(loadingEnd(book))
        }
      )
  }
}

export function deleteBook(id) {
  return dispatch => {
    dispatch(loadingStart)
    server.deleteBook(id)
      .then(deletingEnd)
      .then(dispatch.bind(null, {
        type: BOOK_LIBRARY_UPDATE
      }))
  }
}

export function goBack() {
  return dispatch => {
    history.goBack()
  }
}
