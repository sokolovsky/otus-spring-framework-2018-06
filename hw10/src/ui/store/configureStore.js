import { createStore, applyMiddleware, compose } from 'redux'
import { rootReducer } from '../reducers'
import { logger } from 'redux-logger'
import thunk from 'redux-thunk'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import { createBrowserHistory } from 'history'

export const history = createBrowserHistory()

export const store = createStore(
  connectRouter(history)(rootReducer),
  compose(
    applyMiddleware(
      routerMiddleware(history),
      thunk,
      logger
    ),
  ),
)
