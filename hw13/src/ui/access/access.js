import server from '../server/server'

export const access = {
  isAuthenticated () {
    return server.hasValidToken()
  },
  can(action) {
    return this.isAuthenticated()
  }
}
