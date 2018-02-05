
CREATE TABLE user_states
(
	id serial PRIMARY KEY NOT NULL,
	state varchar(255) NOT NULL,
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	unique(state, deleted)
);


CREATE TABLE users
(
	id serial PRIMARY KEY NOT NULL,
	user_name varchar(255) UNIQUE NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) UNIQUE NOT NULL,
	encrypted_password varchar(255) NOT NULL,
	salt varchar(255) UNIQUE NOT NULL,
	user_state_id integer NOT NULL REFERENCES user_states(id),
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);


CREATE UNIQUE INDEX users_user_name ON users(user_name);


CREATE TABLE users_history
(
	id serial PRIMARY KEY NOT NULL,
	user_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	encrypted_password varchar(255) NOT NULL,
	salt varchar(255) NOT NULL,
	user_state_id integer NOT NULL REFERENCES user_states(id),
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);


CREATE TABLE user_roles
(
	id serial PRIMARY KEY NOT NULL,
	role_name varchar(255) NOT NULL,
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	UNIQUE(role_name, deleted)
);


CREATE TABLE users_roles_links
(
	id serial PRIMARY KEY NOT NULL,
	user_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	role_id integer NOT NULL REFERENCES user_roles(id) ON DELETE CASCADE,
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	UNIQUE(user_id, role_id, deleted)
);


CREATE FUNCTION insert_users_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO users_history(user_id, first_name, last_name, email, encrypted_password, salt, user_state_id)
VALUES(OLD.id, OLD.first_name, OLD.last_name, OLD.email, OLD.encrypted_password, OLD.salt, OLD.user_state_id);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;
	

CREATE TRIGGER update_users
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE PROCEDURE insert_users_history();

----------------------------------------------------------------------------------------


CREATE TABLE faculty_ranks
(
	id serial PRIMARY KEY NOT NULL,
	rank_name varchar(255) NOT NULL,
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	UNIQUE(rank_name, deleted)
);


CREATE TABLE faculty
(
	id serial PRIMARY KEY NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	rank_id integer
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	UNIQUE(email, deleted)
);


CREATE TABLE faculty_history
(
	id serial PRIMARY KEY NOT NULL,
	faculty_information_id integer NOT NULL REFERENCES faculty(id) ON DELETE CASCADE
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) UNIQUE NOT NULL,
	rank_id integer
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);




CREATE TABLE error_logs
(
	id serial PRIMARY KEY NOT NULL,
	user_id integer REFERENCES users(id) ON DELETE CASCADE,
	error_text text,
	created_at timestamp without time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

CREATE UNIQUE INDEX error_logs_user_id ON error_logs(user_id);

--logged_requests

--------------------------------------

DROP TRIGGER update_users ON users;
DROP FUNCTION insert_users_history();

DROP TABLE users_roles_links;
DROP TABLE user_roles;
DROP TABLE users_history;
DROP TABLE users;
DROP TABLE user_states;