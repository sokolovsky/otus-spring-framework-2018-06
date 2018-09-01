import { API_HOST } from '../constants'

const api = API_HOST

const request = (path) => {
  return new Request(api + path)
}

export default {
  getBookList(filter) {
    return fetch(request('/book/list')).then((response) => {
      return response.json()
    })
  },
  getBook(id) {
    return fetch(request('/book/get/' + id)).then((response) => {
      return response.json()
    })
  },
  deleteBook(id) {
    return fetch(request('/book/delete/' + id), { method: 'post'}).then((response) => {
      return response.json()
    })
  },
  saveBook(fields) {
    let url = '/book/add'
    if (fields.id) {
      url = '/book/update' + fields.id
    }
    return fetch(request(url), { method: 'post'}).then((response) => {
      return response.json()
    })
  },
  getGenreList() {
    return fetch(request('/genre/list'), { method: 'post'}).then((response) => {
      return response.json()
    })
  },
  getAuthorList() {
    return fetch(request('/author/list'), { method: 'post'}).then((response) => {
      return response.json()
    })
  }
}
