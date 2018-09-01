import {
  ACTION_BOOK_LIST_LOAD_START,
  ACTION_BOOK_LIST_LOADED, BOOK_LIBRARY_UPDATE,
} from '../constants'

const initialState = {
  items: [],
  change: true
}

export function bookListReducer(state = initialState, action) {
  switch (action.type) {
    case BOOK_LIBRARY_UPDATE:
      return {...state, ...{change: true}}
    case ACTION_BOOK_LIST_LOADED:
      return {...state, ...action.payload}
    case ACTION_BOOK_LIST_LOAD_START:
    default:
      return state
  }
}
