import {
  ACTION_AUTHOR_LIST_LOAD_START,
  ACTION_AUTHOR_LIST_LOADED
} from '../constants'

const initialState = {
  items: [],
}

export function authorListReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_AUTHOR_LIST_LOADED:
      return {...state, ...action.payload}
    case ACTION_AUTHOR_LIST_LOAD_START:
    default:
      return state
  }
}
