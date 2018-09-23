import {
  ACTION_GENRE_LIST_LOADED,
  ACTION_GENRE_LIST_LOAD_START
} from '../constants'

const initialState = {
  items: [],
}

export function genreListReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_GENRE_LIST_LOADED:
      return {...state, ...action.payload}
    case ACTION_GENRE_LIST_LOAD_START:
    default:
      return state
  }
}
