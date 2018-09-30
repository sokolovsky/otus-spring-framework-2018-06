import server from '../server/server'
import {
  ACTION_AUTH_INFO_LOAD_START,
  ACTION_AUTH_INFO_LOADED,
  ACTION_AUTH_FAILED,
  ACTION_AUTH_SUCCESSFUL,
  ACTION_AUTH_LOGOUT
} from "../constants";

const loadingStart = () => {
  return {
    type: ACTION_AUTH_INFO_LOAD_START
  }
}

const infoLoaded = (info) => {
  return {
    type: ACTION_AUTH_INFO_LOADED,
    payload: {info}
  }
}

const authenticationSuccessful = (result) => {
  return {
    type:ACTION_AUTH_SUCCESSFUL,
    payload: {
      username: result.username
    }
  }
}

export function loadInfo() {
  return dispatch => {
    dispatch(loadingStart())
    server.getAuthenticateInfo()
      .then((info) => {
        dispatch(infoLoaded(info))
      });
  }
}

export function login(login, password) {
  return dispatch => {
    dispatch(loadingStart())
    server.tryLogin(login, password)
      .then((result) => {
        if (!result.success) {
          dispatch({type: ACTION_AUTH_FAILED})
          return
        }
        dispatch(authenticationSuccessful(result))
      })
  }
}

export function logout() {
  return dispatch => {
    dispatch(loadingStart())
    server.logout().then(() => dispatch({type: ACTION_AUTH_LOGOUT}))
  }
}
