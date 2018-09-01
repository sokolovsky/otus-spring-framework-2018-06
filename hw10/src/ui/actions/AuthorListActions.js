import server from '../server/server'
import { ACTION_AUTHOR_LIST_LOAD_START, ACTION_AUTHOR_LIST_LOADED } from '../constants'

const loadingStart = {
  type: ACTION_AUTHOR_LIST_LOAD_START
}

const loadingEnd = (items) => {
  return {
    type: ACTION_AUTHOR_LIST_LOADED,
    payload: {
      items
    }
  }
}

export function loadAuthorList() {
  return dispatch => {
    dispatch(loadingStart)
    server.getAuthorList()
      .then(
        (items) => {
          dispatch(loadingEnd(items))
        }
      )
  }
}