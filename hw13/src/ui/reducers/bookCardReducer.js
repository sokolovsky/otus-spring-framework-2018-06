import {access} from '../access/access'
import {
  ACTION_AUTH_LOGOUT, ACTION_AUTH_SUCCESSFUL,
  ACTION_BOOK_CARD_LOAD_START,
  ACTION_BOOK_CARD_LOADED,
  ACTION_BOOK_CAN_EDIT_CARD_LOADED,
  ACTION_BOOK_CAN_DELETE_CARD_LOADED, ACTION_BOOK_CAN_EDIT_CARD_LOADING, ACTION_BOOK_CAN_DELETE_CARD_LOADING,

} from '../constants'

const initialState = {
  book: {
    id: '',
    authors: [],
    title: '',
    isbn: '',
    genres: [],
  },
  canEdit: 'LOAD',
  canDelete: 'LOAD'
};

export function bookCardReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_BOOK_CARD_LOADED:
      return {...state, ...action.payload};
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{canEdit: false, canDelete: false}};
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{canEdit: 'LOAD', canDelete: 'LOAD'}};
    case ACTION_BOOK_CAN_EDIT_CARD_LOADING:
      return {...state, ...{canEdit: 'LOADING'}};
    case ACTION_BOOK_CAN_EDIT_CARD_LOADED:
      return {...state, ...{canEdit: action.payload.result}};
    case ACTION_BOOK_CAN_DELETE_CARD_LOADING:
      return {...state, ...{canDelete: 'LOADING'}};
    case ACTION_BOOK_CAN_DELETE_CARD_LOADED:
      return {...state, ...{canDelete: action.payload.result}};
    case ACTION_BOOK_CARD_LOAD_START:
    default:
      return state
  }
}
