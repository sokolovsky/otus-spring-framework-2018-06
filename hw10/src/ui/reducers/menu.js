const initialState = {
  items: {
    '/': 'Книги',
    '/authors/': 'Авторы',
    '/genres/': 'Жанры'
  },
  active: '/',
}

export function menuReducer(state = initialState) {
  return state
}
