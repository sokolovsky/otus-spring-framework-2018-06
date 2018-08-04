insert into `authors` values (null, 'Фёдор Тютчев');
insert into `authors` values (null, 'Николай Гоголь');
insert into `authors` values (null, 'Александр Пушкин');

insert into `genres` values (null, 'Классическая проза');
insert into `genres` values (null, 'Литература 19 века');
insert into `genres` values (null, 'Русская классика');
insert into `genres` values (null, 'Древнерусская литература');

insert into `books` values (null, 'Повести Белкина (сборник)', '978-5-91045-983-4');
insert into `books` values (null, 'Евгений Онегин', '978-5-389-09531-1');
insert into `books` values (null, 'Сказка о царе Салтане', '978-5-9930-2218-5');

insert into `books` values (null, 'Федор Тютчев: Стихи', '978-5-9905833-8-2');
insert into `books` values (null, 'Федор Тютчев: Стихи детям', '978-5-99058313-8-2');

insert into `books` values (null, 'Вечера на хуторе близ Диканьки', '978-5-17-099707-7');
insert into `books` values (null, 'Петербургские повести', '978-5-91045-970-4');

insert into `books_genres` values (null, 1, 1);
insert into `books_genres` values (null, 1, 2);
insert into `books_genres` values (null, 2, 2);
insert into `books_genres` values (null, 3, 2);
insert into `books_genres` values (null, 4, 1);
insert into `books_genres` values (null, 4, 4);
insert into `books_genres` values (null, 5, 1);
insert into `books_genres` values (null, 6, 2);
insert into `books_genres` values (null, 7, 3);

insert into `books_authors` values (null, 1, 3);
insert into `books_authors` values (null, 2, 3);
insert into `books_authors` values (null, 3, 3);

insert into `books_authors` values (null, 4, 1);
insert into `books_authors` values (null, 5, 1);

insert into `books_authors` values (null, 6, 2);
insert into `books_authors` values (null, 7, 2);
insert into `books_authors` values (null, 7, 3);
