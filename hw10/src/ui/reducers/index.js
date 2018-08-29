import { combineReducers } from 'redux'
import { menuReducer } from './menu'
import { headerReducer } from './header'
import { bookListReducer } from './bookListReducer'
import { authorListReducer } from './authorListReducer'
import { genreListReducer } from './genreListReducer'
import { bookCardReducer } from './bookCardReducer'

export const rootReducer = combineReducers({
  menu: menuReducer,
  header: headerReducer,
  bookList: bookListReducer,
  bookCard: bookCardReducer,
  authorList: authorListReducer,
  genreList: genreListReducer
})
