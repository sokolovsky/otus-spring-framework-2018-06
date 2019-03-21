import implement, {Interface, type} from 'implement-js';

import mock from './mock';
import rest from './rest';

const ServerInterface = Interface('Server')({
  getBookList: type('function'),
  getBook: type('function'),
  deleteBook: type('function'),
  getGenreList: type('function'),
  getAuthorList: type('function'),
  saveBook: type('function'),
  liveBookComment: type('function'),
  getBookComments: type('function'),
  getAuthenticateInfo: type('function'),
  tryLogin: type('function'),
  logout: type('function'),
  hasValidToken: type('function'),
  canEditBook: type('function'),
  canAddBook: type('function'),
  canDeleteBook: type('function'),
  canLeaveComment: type('function'),
});

let server = mock;

if (process.env.NODE_ENV === 'production') {
  server = rest;
}

implement(ServerInterface)(server);

export default server;
