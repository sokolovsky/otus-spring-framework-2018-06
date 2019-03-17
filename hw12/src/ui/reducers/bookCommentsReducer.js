import {access} from '../access/access'
import {
  ACTION_BOOK_COMMENTS_LOADED,
  ACTION_BOOK_COMMENTS_LOAD_START,
  ACTION_BOOK_COMMENTS_SEND_ONE, ACTION_AUTH_LOGOUT, ACTION_AUTH_SUCCESSFUL,
} from '../constants'

function canComment() {
  return access.can('canComment');
}

const initialState = {
  items: [],
  load: false,
  canComment: canComment()
}

export function bookCommentsReducer(state = initialState, action) {
  switch (action.type) {
    case
    ACTION_BOOK_COMMENTS_SEND_ONE:
      return {...state, ...{load: action.payload.success !== false}}
    case ACTION_BOOK_COMMENTS_LOADED:
      return {...state, ...action.payload, ...{load: false}}
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{canComment: canComment()}}
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{canComment: canComment()}}
    case ACTION_BOOK_COMMENTS_LOAD_START:
    default:
      return state
  }
}
