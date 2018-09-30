import {Component} from 'react'
import React from 'react'
import PropTypes from 'prop-types'
import Authenticate from '../containers/Authenticate'
import {Navbar, Nav, NavItem} from "react-bootstrap";


export class Header extends Component {
  render() {
    const title = this.props.title
    return <Navbar>
      <div className="col-xs-6">
        <Navbar.Header>
          <Navbar.Brand>{title}</Navbar.Brand>
        </Navbar.Header>
      </div>
      <div className="col-xs-6">
        <Nav pullRight={true}>
          <NavItem eventKey={1}><Authenticate/></NavItem>
        </Nav>
      </div>
    </Navbar>
  }
}

Header.propTypes = {
  title: PropTypes.string.isRequired
}
