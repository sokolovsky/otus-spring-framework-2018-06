const mockPromise  = function () {
  const args = arguments
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve && resolve.apply(null, args)
    }, 500)
  })
}

export default {
  getBookList() {
    return mockPromise([
      {
        'author': 'Иван Бунин',
        'title': 'Некое произведение Ивана Бунина',
        'isbn': '9023896753735-542653',
        'genres': ['Научная фантастика', 'Коммерческая проза' , 'Записки охотника']
      },
      {
        'author': 'Иван Бунин',
        'title': 'Другое произведение Ивана Бунина',
        'isbn': '9023896753735-532653',
        'genres': ['Коммерческая проза' , 'Записки охотника']
      },
    ])
  },
  getAuthorList () {
    return mockPromise([
      {
        id: 1,
        name: 'Иван Бунин',
        bookCount: 10,
      },
      {
        id: 2,
        name: 'Василий Уткин',
        bookCount: 7
      }
    ])
  },
  getGenreList() {
    return mockPromise([
      {
        id: 1,
        name: 'Фантастика',
        bookCount: 10,
      },
      {
        id: 2,
        name: 'Классическая проза',
        bookCount: 71
      },
      {
        id: 4,
        name: 'Просто проза',
        bookCount: 1
      }
    ])
  },
}
