import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from "react-redux"
import { history, store } from './store/configureStore'

import App from './containers/App'

ReactDOM.render(
    <Provider store={store}>
      <App history={history}/>
    </Provider>,
    document.getElementById('root')
)
