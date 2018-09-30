import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {bindActionCreators} from "redux";
import * as Actions from "../actions/AuthenticateActions";
import connect from "react-redux/es/connect/connect";
import {Button, Modal, Alert} from "react-bootstrap";
import {FormField} from "../components/FormField";



class Authenticate extends Component {

  constructor(props, context) {
    super(props, context)

    this.handleShow = this.handleShow.bind(this)
    this.handleClose = this.handleClose.bind(this)
    this.handleLogin = this.handleLogin.bind(this)
    this.handleLogout = this.handleLogout.bind(this)

    this.state = {
      show: false
    }

    this.references = {
      login: React.createRef(),
      password: React.createRef(),
    }
  }

  componentWillMount() {
    this.props.actions.loadInfo()
  }

  handleClose() {
    this.setState({ show: false })
  }

  handleShow() {
    this.setState({ show: true })
  }

  handleLogin() {
    const data = this.getData()
    this.props.actions.login(data.login, data.password)
  }

  handleLogout() {
    this.props.actions.logout()
  }

  getData() {
    return {
      login: this.references.login.current.value,
      password: this.references.password.current.value
    }
  }

  render() {
    const {isAuthenticated, username} = this.props
    if (isAuthenticated) {
      return <div>
        <span style={{marginRight: "10px"}}>{username}</span><Button bsStyle="primary" onClick={this.handleLogout}>Выйти</Button>
      </div>
    }
    return <div>
        <Button bsStyle="primary" onClick={this.handleShow}>
          Войти
        </Button>
        <Modal show={this.state.show} onHide={this.handleClose}>
          <Modal.Header closeButton>
          </Modal.Header>
          <Modal.Body>
            <FormField label="Логин" forId="login">
              <input type="text" name="login" id="login" className="form-control" ref={this.references.login}/>
            </FormField>
            <FormField label="Пароль" forId="password">
              <input type="password" name="password" id="password" className="form-control" ref={this.references.password}/>
            </FormField>
            {this.props.authfail && <Alert bsStyle="danger">Пользователя с таким логином и паролем не существует</Alert>}
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={this.handleLogin} bsStyle="primary">Войти</Button>
            <Button onClick={this.handleClose}>Закрыть</Button>
          </Modal.Footer>
        </Modal>
      </div>
  }
}

Authenticate.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  username: PropTypes.string,
  actions: PropTypes.objectOf(PropTypes.func).isRequired

}

const mapStateToProps = (state) => {
  return {...state.authenticate}
}

const mapDispatchToProps = (dispatch) => {
  return {
    actions: bindActionCreators(Actions, dispatch)
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Authenticate)
