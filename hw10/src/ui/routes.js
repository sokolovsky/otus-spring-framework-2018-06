import React from 'react'
import { Route, IndexRoute } from 'react-router'

import App from 'containers/App/App'
import Page from 'containers/Page/Page'
import Home from 'containers/Home/Home'
import Lk from 'containers/LK/LK/Lk'
import Lesson from 'containers/Lesson/Lesson'
import Catalog from 'containers/Catalog/Catalog'
import DetailCart from 'containers/DetailCart/DetailCart'
import LessonsList from 'containers/LessonsList/LessonsList'
import Competition from 'containers/Competition/Competition'
import Sculpting from 'containers/Sculpting/Sculpting'
import LkProfile from 'containers/LK/LKProfile/Profile'
import LKWrap from 'containers/LK/LKWrap/LKWrap'
import LkProfileChild from 'containers/LK/LKChild/Child'
import LkSubscription from 'containers/LK/LKSubscription/Subscription'
import SculptingSectionList from 'containers/Sculpting/SculptingSectionList/SculptingSectionList'
import RegistrationConfirmation from 'containers/RegistrationConfirmation/RegistrationConfirmation'
import ResetPassword from 'containers/ResetPassword/ResetPassword'
import ChangePassword from 'containers/ChangePassword/ChangePassword'
import AboutBrand from 'containers/AboutBrand/AboutBrand'

import NotFound from 'containers/NotFound/NotFound'

export const routes = (
  <div>
    <Route path='/day' component={Page} />
    <Route path='/' component={App}>
      <IndexRoute component={Home} />
      <Route path='/brand' component={AboutBrand} />
      <Route path='/lessons/:id' component={Lesson} />
      <Route path='/lessons' component={LessonsList} />
      <Route path='/competition' component={Competition} />
      <Route path='/catalog' component={Catalog} />
      <Route path='/detail-cart/:id' component={DetailCart} />
      <Route path='/sculpting' component={Sculpting}>
        <Route path='/sculpting/:code' component={SculptingSectionList} />
      </Route>
      <Route path='/lk' component={Lk} />
      <Route component={LKWrap}>
        <Route path='/lk/profile' component={LkProfile} />
        <Route path='/lk/child' component={LkProfileChild} />
        <Route path='/lk/subscription' component={LkSubscription} />
      </Route>
      <Route path='/registration-confirmation' component={RegistrationConfirmation} />
      <Route path='/reset-password' component={ResetPassword} />
      <Route path='/change-password' component={ChangePassword} />
      <Route path='*' component={NotFound} />
    </Route>
  </div>
)
