import { combineReducers } from 'redux'
import { menuReducer } from './menu'
import { headerReducer } from './header'
import { bookListReducer } from './booklistReducer'

export const rootReducer = combineReducers({
  menu: menuReducer,
  header: headerReducer,
  bookList: bookListReducer,
})
