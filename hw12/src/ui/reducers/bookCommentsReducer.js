import {
  ACTION_BOOK_COMMENTS_LOADED,
  ACTION_BOOK_COMMENTS_LOAD_START,
  ACTION_BOOK_COMMENTS_SEND_ONE,
} from '../constants'

const initialState = {
  items: [],
  load: false
}

export function bookCommentsReducer(state = initialState, action) {
  switch (action.type) {
    case
    ACTION_BOOK_COMMENTS_SEND_ONE:
      return {...state, ...{load: action.payload.success !== false}}
    case ACTION_BOOK_COMMENTS_LOADED:
      return {...state, ...action.payload, ...{load: false}}
    case ACTION_BOOK_COMMENTS_LOAD_START:
    default:
      return state
  }
}
