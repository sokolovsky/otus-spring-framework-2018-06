import {
  ACTION_AUTH_LOGOUT, ACTION_AUTH_SUCCESSFUL,
  ACTION_BOOK_LIST_LOAD_START,
  ACTION_BOOK_LIST_LOADED, BOOK_LIBRARY_UPDATE,
  ACTION_BOOK_LIST_CAN_ADD_LOADED,
  ACTION_BOOK_LIST_CAN_ADD_LOADING
} from '../constants'

const initialState = {
  items: [],
  change: true,
  canAdd: 'LOAD'
};

export function bookListReducer(state = initialState, action) {
  switch (action.type) {
    case BOOK_LIBRARY_UPDATE:
      return {...state, ...{change: true}};
    case ACTION_BOOK_LIST_LOADED:
      return {...state, ...action.payload};
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{canAdd: false}};
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{canAdd: 'LOAD'}};
    case ACTION_BOOK_LIST_CAN_ADD_LOADING:
      return {...state, ...{canAdd: 'LOADING'}};
    case ACTION_BOOK_LIST_CAN_ADD_LOADED:
      return {...state, ...{canAdd: action.payload.result}};
    case ACTION_BOOK_LIST_LOAD_START:
    default:
      return state
  }
}
