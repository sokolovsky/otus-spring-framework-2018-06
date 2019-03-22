import {
  ACTION_BOOK_LIST_LOAD_START, ACTION_BOOK_LIST_LOADED,
  ACTION_BOOK_LIST_CAN_ADD_LOADED,
  ACTION_BOOK_LIST_CAN_ADD_LOADING
} from '../constants'
import server from '../server/server'


const loadingStart = {
  type: ACTION_BOOK_LIST_LOAD_START
};

const loadingEnd = (items) => {
  return {
    type: ACTION_BOOK_LIST_LOADED,
    payload: {
      items
    }
  }
};

export function loadBookList(filter) {
  return dispatch => {
    dispatch(loadingStart);
    server.getBookList(filter)
      .then(
        (items) => {
          dispatch(loadingEnd(items))
        }
      )
  }
}

export function loadCanAdd() {
  return dispatch => {
    dispatch({type: ACTION_BOOK_LIST_CAN_ADD_LOADING});
    server.canAddBook()
      .then(
        (res) => {
          dispatch({
            type: ACTION_BOOK_LIST_CAN_ADD_LOADED,
            payload: {
              success: res.success,
              result: res.success ? res.data.result : false
            }
          })
        }
      )
  }
}
