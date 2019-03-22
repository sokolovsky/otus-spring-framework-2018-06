import server from '../server/server'
import { history } from '../store/configureStore'
import {
  ACTION_BOOK_CAN_DELETE_CARD_LOADED,
  ACTION_BOOK_CAN_DELETE_CARD_LOADING,
  ACTION_BOOK_CAN_EDIT_CARD_LOADED,
  ACTION_BOOK_CAN_EDIT_CARD_LOADING,
  ACTION_BOOK_CARD_DELETED,
  ACTION_BOOK_CARD_LOAD_START,
  ACTION_BOOK_CARD_LOADED,
  BOOK_LIBRARY_UPDATE,
} from '../constants'

const loadingStart = () => {
  return {
    type: ACTION_BOOK_CARD_LOAD_START
  }
};

const deletingEnd = () => {
  return {
    type: ACTION_BOOK_CARD_DELETED
  }
};

const canEditState = (res) => {
  return {
    type: ACTION_BOOK_CAN_EDIT_CARD_LOADED,
    payload: {success: res.success, result: res.success ? res.data.result : false}
  }
};
const canDeleteState = (res) => {
  return {
    type: ACTION_BOOK_CAN_DELETE_CARD_LOADED,
    payload: {success: res.success, result: res.success ? res.data.result : false}
  }
};

const loadingEnd = (book) => {
  return {
    type: ACTION_BOOK_CARD_LOADED,
    payload: {book: book},
  }
};

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

export function loadCanEditBook(id) {
  return dispatch => {
    dispatch({type: ACTION_BOOK_CAN_EDIT_CARD_LOADING});
    server.canEditBook(id)
      .then(
        (res) => {
          dispatch(canEditState(res))
        }
      )
  }
}

export function loadCanDeleteBook(id) {
  return dispatch => {
    dispatch({type: ACTION_BOOK_CAN_DELETE_CARD_LOADING});
    server.canDeleteBook(id)
      .then(
        (res) => {
          dispatch(canDeleteState(res))
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
