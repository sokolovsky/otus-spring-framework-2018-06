import server from '../server/server'
import {
  ACTION_BOOK_COMMENTS_LOAD_START,
  ACTION_BOOK_COMMENTS_LOADED,
  ACTION_BOOK_COMMENTS_SEND_ONE,
} from '../constants'

const loadingStart = () => {
  return {
    type: ACTION_BOOK_COMMENTS_LOAD_START
  }
}

const loadingEnd = (book) => {
  return {
    type: ACTION_BOOK_COMMENTS_LOADED,
    payload: {items: book},
  }
}

const commentSentActionContainer = (res) => {
  return {
    type: ACTION_BOOK_COMMENTS_SEND_ONE,
    payload: {success: res.success}
  }
}

export function loadBookComments(bookId) {
  return dispatch => {
    dispatch(loadingStart)
    server.getBookComments(bookId)
      .then(
        (book) => {
          dispatch(loadingEnd(book))
        }
      )
  }
}

export function sendComment(bookId, text) {
  return dispatch => {
    dispatch(loadingStart)
    server.liveBookComment(bookId, text)
      .then(
        (res) => {
          dispatch(commentSentActionContainer(res))
        }
      )
  }
}
