import { API_HOST } from '../constants'

const api = API_HOST;

const tokenService = {
  save(value) {
    sessionStorage.setItem('authToken', value)
  },
  get() {
    return sessionStorage.getItem('authToken')
  },
  clear() {
    sessionStorage.removeItem('authToken')
  },
  has() {
    return !!this.get()
  }
};

const request = (path) => {
  return new Request(api + path)
};

const post = (request, params, filter) => {
  const headers = {
    'X-AuthToken': tokenService.get()
  };
  return fetch(request, {headers, ...(params || {}), ...{method: 'POST'}})
    .then(filter || (res => res.json()))
};

const get = (request, params, filter) => {
  const headers = {
    'X-AuthToken': tokenService.get()
  };
  return fetch(request, {headers, ...(params || {}), ...{method: 'GET'}})
    .then(filter || (res => res.json()))
};

const mergeListWithStatistic = (list, entityField) => {
  const res = list[entityField] || [];
  res.forEach(i => {
    i.bookCount = list["statistic"][i.id]
  });
  return res
};

const flatAuthorsAndGenresForBook = (book) => {
  let genres = book.genres;
  book.genres = {};
  genres.forEach(g => {
    book.genres[g.id] = g.title
  });

  let authors = book.authors;
  book.authors = {};
  authors.forEach(g => {
    book.authors[g.id] = g.name
  });
  return book
};

export default {
  getBookList(filter) {
    filter = filter || {};
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
    let url = '/book/add';
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
  },
  getAuthenticateInfo() {
    if (!tokenService.has()) {
      return new Promise((resolve, reject) => {
        return function() {
          if (resolve) {
            return resolve.apply(null, arguments);
          }
        }
      })
    }
    return post(request('/token/test'));
  },
  tryLogin(login, password) {
      return post(request('/login'), {
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          login, password
        })
      }, res => {
        if (res.status !== 200) {
          return {
            success: false
          }
        }
        return res.json()
          .then(data => {
            tokenService.save(data.token);
            return {
              success: true,
              username: data.username,
              token: data.token
            }
          })
      })
  },
  logout() {
    tokenService.clear();
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve && resolve.apply(null, {})
      }, 1)
    })
  },
  hasValidToken() {
    return tokenService.has()
  },
  canEditBook(bookId) {
    return get(request('/book/canEdit/' + bookId))
  },
  canDeleteBook(bookId) {
    return get(request('/book/canDelete/' + bookId));
  },
  canAddBook() {
    return get(request('/book/canAdd'))
  },
  canLeaveComment(bookId) {
    return get(request('/comment/canLeave/'+bookId))
  }
}
