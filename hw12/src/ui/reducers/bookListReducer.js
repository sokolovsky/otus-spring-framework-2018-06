import {access} from "../access/access";
import {
  ACTION_AUTH_LOGOUT, ACTION_AUTH_SUCCESSFUL,
  ACTION_BOOK_LIST_LOAD_START,
  ACTION_BOOK_LIST_LOADED, BOOK_LIBRARY_UPDATE,
} from '../constants'

function canEdit() {
  return access.can('bookEdit')
}

const initialState = {
  items: [],
  change: true,
  canEdit: canEdit()
}

export function bookListReducer(state = initialState, action) {
  switch (action.type) {
    case BOOK_LIBRARY_UPDATE:
      return {...state, ...{change: true}}
    case ACTION_BOOK_LIST_LOADED:
      return {...state, ...action.payload}
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{canEdit: canEdit()}}
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{canEdit: canEdit()}}
    case ACTION_BOOK_LIST_LOAD_START:
    default:
      return state
  }
}
