import React, { Component } from 'react'
import { ConnectedRouter } from 'connected-react-router'
import { Header } from '../components/Header'
import { Menu } from '../components/Menu'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

class App extends Component {

    render() {
      const {menu, header, history} = this.props;
      return <div className="container-fluid">
        <Header title={header.title}/>
        <ConnectedRouter
          history={history}
        >
          <Menu items={menu.items} active={menu.active}/>
        </ConnectedRouter>
      </div>
    }
};

App.propTypes = {
  menu: PropTypes.object.isRequired,
  header: PropTypes.object.isRequired
}

const mapStateToProps = store => {
  return {
    menu: store.menu,
    header: store.header,
  }
}

export default connect(mapStateToProps)(App)
