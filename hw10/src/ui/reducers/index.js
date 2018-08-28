import { combineReducers } from 'redux'
import { menuReducer } from './menu'
import { headerReducer } from './header'
import { bookListReducer } from './booklistReducer'
import { authorListReducer } from './authorlistReducer'
import { genreListReducer } from './genrelistReducer'

export const rootReducer = combineReducers({
  menu: menuReducer,
  header: headerReducer,
  bookList: bookListReducer,
  authorList: authorListReducer,
  genreList: genreListReducer
})
