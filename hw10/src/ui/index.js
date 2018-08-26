import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from "react-redux";

import App from './components/App'

ReactDOM.render(
    <Provider store={store}>
        <Router
            history={browserHistory}
            routes={routes}
        />
    </Provider>,
    document.getElementById('root')
)