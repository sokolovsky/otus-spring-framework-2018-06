import {
  ACTION_BOOK_LIST_LOAD_START,
  ACTION_BOOK_LIST_LOADED
} from '../constants'

const initialState = {
  items: [],
}

export function bookListReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_BOOK_LIST_LOADED:
      return {...state, ...{bookList: action.payload}}
    case ACTION_BOOK_LIST_LOAD_START:
    default:
      return state
  }
}
