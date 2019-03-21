import {
  ACTION_BOOK_COMMENTS_LOADED,
  ACTION_BOOK_COMMENTS_LOAD_START,
  ACTION_BOOK_COMMENTS_SEND_ONE, ACTION_AUTH_LOGOUT, ACTION_AUTH_SUCCESSFUL,
  ACTION_CAN_LEAVE_BOOK_COMMENT_LOADED
} from '../constants'

const initialState = {
  items: [],
  load: false,
  canComment: 'LOAD'
};

export function bookCommentsReducer(state = initialState, action) {
  switch (action.type) {
    case
    ACTION_BOOK_COMMENTS_SEND_ONE:
      return {...state, ...{load: action.payload.success !== false}};
    case ACTION_BOOK_COMMENTS_LOADED:
      return {...state, ...action.payload, ...{load: false}};
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{canComment: false}};
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{canComment: 'LOAD'}};
    case ACTION_CAN_LEAVE_BOOK_COMMENT_LOADED:
      return {...state, ...{canComment: action.payload.canComment || false}};
    case ACTION_BOOK_COMMENTS_LOAD_START:
    default:
      return state
  }
}
