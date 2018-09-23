import server from '../server/server'
import { ACTION_GENRE_LIST_LOAD_START, ACTION_GENRE_LIST_LOADED } from '../constants'


const loadingStart = {
  type: ACTION_GENRE_LIST_LOAD_START
}

const loadingEnd = (items) => {
  return {
    type: ACTION_GENRE_LIST_LOADED,
    payload: {
      items
    }
  }
}

export function loadGenreList() {
  return dispatch => {
    dispatch(loadingStart)
    server.getGenreList()
      .then(
        (items) => {
          dispatch(loadingEnd(items))
        }
      )
  }
}