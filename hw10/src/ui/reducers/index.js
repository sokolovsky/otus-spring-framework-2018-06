import { combineReducers } from 'redux'
import { menuReducer } from './menu'
import { headerReducer } from './header'

export const rootReducer = combineReducers({
  menu: menuReducer,
  header: headerReducer,
})
