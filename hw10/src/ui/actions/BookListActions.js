import { ACTION_BOOK_LIST_LOAD_START, ACTION_BOOK_LIST_LOADED } from '../constants'
import server from '../server/server'


const loadingStart = {
  type: ACTION_BOOK_LIST_LOAD_START
}

const loadingEnd = (items) => {
  return {
    type: ACTION_BOOK_LIST_LOADED,
    payload: {
      items
    }
  }
}

export function loadBookList() {
  return dispatch => {
    dispatch(loadingStart)
    server.getBookList()
      .then(
        (items) => {
          dispatch(loadingEnd(items))
        }
      )
  }
}