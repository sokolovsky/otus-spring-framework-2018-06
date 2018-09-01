import React from 'react'
import { Route, Switch } from 'react-router'

import BookList  from './containers/BookList'
import BookCard from './containers/BookCard'
import AuthorsList from './containers/AuthorList'
import GenreList from './containers/GenreList'
import BookForm from './containers/BookForm'

export const routes = (
  <div>
    <Switch>
      <Route path="/book/card/:id" component={BookCard} />
      <Route path="/book/edit/:id" component={BookForm} />
      <Route path="/book/add/" component={BookForm} />
      <Route exact path="/" component={BookList} />
      <Route exact path="/authors/" component={AuthorsList} />
      <Route exact path="/genres/" component={GenreList} />
      <Route exact path="/authors/:author" component={BookList} />
      <Route exact path="/genres/:genre" component={BookList} />
    </Switch>
  </div>
)
