import { Probability } from './utils/probability'

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
  getBook() {
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
      { success: false, errors: { title: 'title is empty' , isbn: 'isbn need to by not empty'} },
      20)
    probability.addCase({ success: true }, 80)

    const result = probability.getResult()
    console.log(result)
    return mockPromise(result)
  },
}
