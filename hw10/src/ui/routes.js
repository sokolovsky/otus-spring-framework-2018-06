import React from 'react'
import { Route, IndexRoute, Switch } from 'react-router'

import BookList  from './containers/BookList'
import BookCard from './containers/BookCard'
import AuthorsList from './containers/AuthorList'
import GenreList from './containers/GenreList'

export const routes = (
  <div>
    <Switch>
      <Route path="/book/card/:id" component={BookCard} />
      <Route exact path="/" component={BookList} />
      <Route path="/authors/" component={AuthorsList} />
      <Route path="/genres/" component={GenreList} />
    </Switch>
  </div>
)
