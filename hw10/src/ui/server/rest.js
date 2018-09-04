import { API_HOST } from '../constants'

const api = API_HOST

const request = (path) => {
  return new Request(api + path)
}

const post = (request, params) => {
  return fetch(request, {...(params || {}), ...{method: 'POST'}})
    .then(res => res.json())
}

const get = (request, params) => {
  return fetch(request, {...(params || {}), ...{method: 'GET'}})
    .then(res => res.json())
}

const mergeListWithStatistic = (list, entityField) => {
  const res = list[entityField] || []
  res.forEach(i => {
    i.bookCount = list["statistic"][i.id]
  })
  return res
}

const flatAuthorsAndGenresForBook = (book) => {
  let genres = book.genres
  book.genres = {}
  genres.forEach(g => {
    book.genres[g.id] = g.title
  })

  let authors = book.authors
  book.authors = {}
  authors.forEach(g => {
    book.authors[g.id] = g.name
  })
  return book
}

export default {
  getBookList(filter) {
    filter = filter || {}
    return get(request('/book/list?genre=' + (filter.genre || '') + '&author=' + (filter.author || '')))
      .then(list => {
        list.forEach(b => flatAuthorsAndGenresForBook(b))
        return list
      })
  },
  getBook(id) {
    return get(request('/book/get/' + id))
      .then(b => flatAuthorsAndGenresForBook(b))
  },
  deleteBook(id) {
    return post(request('/book/delete/' + id))
  },
  saveBook(fields) {
    let url = '/book/add'
    if (fields.id) {
      url = '/book/update/' + fields.id
    }
    return post(request(url), {
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(fields)
    })
  },
  getGenreList() {
    return get(request('/genre/list')).then((response) => {
      return mergeListWithStatistic(response, "genres")
    })
  },
  getAuthorList() {
    return get(request('/author/list')).then((response) => {
      return mergeListWithStatistic(response, 'authors')
    })
  },
  getBookComments(bookId) {
    return get(request('/comment/book/get/' + bookId))
  },
  liveBookComment(bookId, text) {
    return post(request('/comment/book/add/' + bookId), {
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({text: text})
    })
  }
}
