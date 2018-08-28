import { ACTION_BOOK_LIST_LOAD_START, ACTION_BOOK_LIST_LOADED } from '../constants'


const loadingStart = {
  type: ACTION_BOOK_LIST_LOAD_START
}

const loadingEnd = (items) => {
  return {
    type: ACTION_BOOK_LIST_LOADED,
    payload: {
      items
    }
  }
}

export function loadBookList() {
  return dispatch => {
    dispatch(loadingStart)

    setTimeout(() => {
      return loadingEnd([
        {
          'author': 'Иван Бунин',
          'title': '',
          'isbn': '',
          'genres': ['Научная фантастика', 'Коммерческая проза' , 'Записки охотника']
        }
      ])
    }, 2000)
  }
}