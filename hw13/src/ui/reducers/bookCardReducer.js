import {access} from '../access/access'
import {
  ACTION_AUTH_LOGOUT, ACTION_AUTH_SUCCESSFUL,
  ACTION_BOOK_CARD_LOAD_START,
  ACTION_BOOK_CARD_LOADED
} from '../constants'

function canEdit() {
  return access.can('bookEdit');
}

const initialState = {
  book: {
    id: '',
    authors: [],
    title: '',
    isbn: '',
    genres: [],
  },
  canEdit: canEdit()
}

export function bookCardReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_BOOK_CARD_LOADED:
      return {...state, ...action.payload}
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{canEdit: canEdit()}}
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{canEdit: canEdit()}}
    case ACTION_BOOK_CARD_LOAD_START:
    default:
      return state
  }
}
