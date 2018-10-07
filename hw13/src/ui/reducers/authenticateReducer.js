import {access} from '../access/access'
import {
  ACTION_AUTH_INFO_LOAD_START,
  ACTION_AUTH_INFO_LOADED,
  ACTION_AUTH_FAILED,
  ACTION_AUTH_SUCCESSFUL,
  ACTION_AUTH_LOGOUT
} from '../constants';

const initialState = {
  isAuthenticated: access.isAuthenticated(),
  authfail: false,
  authsuccess: false
}

export function authenticateReducer(state = initialState, action) {
  switch (action.type) {
    case ACTION_AUTH_INFO_LOADED:
      return {...state, ...action.payload}
    case ACTION_AUTH_FAILED:
      return {...state, ...{authfail: true, isAuthenticated: false}}
    case ACTION_AUTH_SUCCESSFUL:
      return {...state, ...{isAuthenticated: true, authfail: false}, ...action.payload}
    case ACTION_AUTH_LOGOUT:
      return {...state, ...{isAuthenticated: false, authfail: false}}
    case ACTION_AUTH_INFO_LOAD_START:
    default:
      return state
  }
}
