const mockPromise  = function () {
  const args = arguments
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve && resolve.apply(null, args)
    }, 500)
  })
}

export default {
  getBookList: function() {
    return mockPromise([
      {
        'author': 'Иван Бунин',
        'title': 'Некое произведение Ивана Бунина',
        'isbn': '9023896753735-542653',
        'genres': ['Научная фантастика', 'Коммерческая проза' , 'Записки охотника']
      }
    ])
  }
}
