DROP TABLE IF EXISTS books_authors;
DROP TABLE IF EXISTS books_genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS authors;

CREATE TABLE genres (
  id    int auto_increment,
  title varchar(255),

  primary key (id)

);

CREATE TABLE authors (
  id   int auto_increment,
  name varchar(255),

  primary key (id)
);

CREATE TABLE books (
  id    int auto_increment,
  title varchar(255),
  ISBN  varchar(40),

  unique (ISBN),
  primary key (id)
);

CREATE TABLE books_genres (
  id      int auto_increment,
  bookId  int,
  genreId int,

  primary key (id),
  foreign key (genreId) references genres (id),
  foreign key (bookId) references books (id)
);

CREATE TABLE books_authors (
  id      int auto_increment,
  bookId  int,
  authorId int,

  primary key (id),
  foreign key (authorId) references authors (id),
  foreign key (bookId) references books (id)
);
