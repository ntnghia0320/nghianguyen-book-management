CREATE TABLE "role" (
    id SERIAL PRIMARY KEY,
    name VARCHAR ( 50 ) UNIQUE NOT NULL
);

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    email VARCHAR ( 50 ) UNIQUE NOT NULL,
	password VARCHAR ( 255 )NOT NULL,
	first_name VARCHAR ( 30 ),
	last_name VARCHAR ( 30 ),
	enabled BOOLEAN,
	avatar VARCHAR ( 255 ),
	role_id INT NOT NULL,
	FOREIGN KEY ( role_id )
      REFERENCES "role" ( id )
);

CREATE TABLE "book" (
    id SERIAL PRIMARY KEY,
    title VARCHAR ( 100 ) NOT NULL,
	author VARCHAR ( 50 ) NOT NULL,
	description VARCHAR ( 255 ),
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	image VARCHAR ( 255 ),
	enabled BOOLEAN,
	user_id INT NOT NULL,
	FOREIGN KEY ( user_id )
      REFERENCES "user" ( id )
);

CREATE TABLE "comment" (
    id SERIAL PRIMARY KEY,
    message TEXT NOT NULL,
	user_id INT NOT NULL,
	book_id INT NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	FOREIGN KEY ( user_id )
      REFERENCES "user" ( id ),
	FOREIGN KEY ( book_id )
      REFERENCES "book" ( id )
);

INSERT INTO "role" (name)
VALUES('ROLE_USER'),
      ('ROLE_ADMIN');

INSERT INTO "user" (email, password, first_name, last_name, enabled, role_id)
VALUES('nguyenvana@gmail.com', 'acmksm#^&%#%387', 'Nguyen', 'Van Admin', TRUE, 2),
      ('nguyenvanb@gmail.com', 'acmksm#^&%#%387', 'Nguyen', 'Van User', TRUE, 1);

INSERT INTO "book" (title, author, created_at, updated_at, enabled, user_id)
VALUES('Dac Nhan Tam', 'Nguyen Van Cale', '2021-07-27 12:12:12', '2021-07-27 12:12:12', TRUE, 1),
      ('Tu Duy Nguoc', 'Tran Van Ok', '2021-07-27 12:12:12', '2021-07-27 12:12:12', TRUE, 2);

INSERT INTO "comment" (message, user_id, book_id, created_at, updated_at)
VALUES('Dac Nhan Tam rat la hay cac ban nen doc no di', 1, 1, '2021-07-27 12:12:12',  '2021-07-27 12:12:12'),
      ('Tu Duy Nguoc cuon sach nay rat thu vi cac ban ne co mot cuon', 1, 2, '2021-07-27 12:12:12', '2021-07-27 12:12:12');
