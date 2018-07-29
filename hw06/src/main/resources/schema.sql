DROP TABLE IF EXISTS comments;
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
  isbn  varchar(40),

  unique (isbn),
  primary key (id)
);

CREATE TABLE books_genres (
  id      int auto_increment,
  book_id  int,
  genre_id int,

  primary key (id),
  foreign key (genre_id) references genres (id),
  foreign key (book_id) references books (id)
);

CREATE TABLE books_authors (
  id      int auto_increment,
  book_id  int,
  author_id int,

  primary key (id),
  foreign key (author_id) references authors (id),
  foreign key (book_id) references books (id)
);

CREATE TABLE comments (
  id    int auto_increment,
  book_id int,
  time timestamp,
  text text,
  primary key (id),
  foreign key (book_id) references books(id)
);

