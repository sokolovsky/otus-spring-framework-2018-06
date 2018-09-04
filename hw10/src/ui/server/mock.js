import { Probability } from '../utils/probability'

const mockPromise  = function () {
  const args = arguments
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve && resolve.apply(null, args)
    }, 500)
  })
}

const serverState = {
  comments: 2,
  incComments() {
    this.comments++
  }
}

export default {
  getBookList(filter) {
    return mockPromise([
      {
        'id': 'eqpowtrghw235252',
        'authors': {'1': 'Иван Бунин'},
        'title': 'Некое произведение Ивана Бунина',
        'isbn': '9023896753735-542653',
        'genres': {'1': 'Научная фантастика', '2': 'Коммерческая проза' , '4': 'Записки охотника'}
      },
      {
        'id': 'eqpowtr231hw235252',
        'authors': {'1': 'Иван Бунин'},
        'title': 'Другое произведение Ивана Бунина',
        'isbn': '9023896753735-532653',
        'genres': {'2': 'Коммерческая проза' , '4': 'Записки охотника'}
      },
      {
        'id': 'eqpowtr231hw245252',
        'authors': {'1': 'Иван Бунин'},
        'title': 'Другое произведение Ивана Бунина',
        'isbn': '9023896753735-53sd53',
        'genres': {'2': 'Коммерческая проза' , '4': 'Записки охотника'}
      },
    ])
  },
  getBook(id) {
    return mockPromise({
      'id': 'eqpowtrghw235252',
      'authors': {'1': 'Иван Бунин'},
      'title': 'Некое произведение Ивана Бунина',
      'isbn': '9023896753735-542653',
      'genres': {'1': 'Научная фантастика', '2': 'Коммерческая проза' , '4': 'Записки охотника'}
    })
  },
  deleteBook(id) {
    return mockPromise({success: true})
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
        title: 'Фантастика',
        bookCount: 10,
      },
      {
        id: 2,
        title: 'Классическая проза',
        bookCount: 71
      },
      {
        id: 4,
        title: 'Просто проза',
        bookCount: 1
      }
    ])
  },
  saveBook: function(data) {
    const probability = new Probability()
    probability.addCase(
      {
        success: false,
        errors: { title: 'title is empty' , isbn: 'isbn need to by not empty', genres: 'error',
          authors: 'error'
        }
      },
      20)
    probability.addCase({ success: true }, 80)

    const result = probability.getResult()
    return mockPromise(result)
  },
  getBookComments(bookId) {
    const template = {
      'time': '25436234-000',
      'text': 'Неплохая книга, но для верстки нужен комментарий поплотнее',
    }
    const newComment = (i) => {
      const item = {...template}
      item.text = i + ' / ' + item.text
      return item
    }

    const parcel = []
    for (let i = 1; i <= serverState.comments; i++) {
      parcel.push(newComment(i))
    }
    return mockPromise(parcel)
  },
  liveBookComment(bookId, text) {
    serverState.incComments()
    return mockPromise({
      success: true
    })
  },
}
