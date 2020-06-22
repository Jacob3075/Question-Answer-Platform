create table if not exists users
(
	id int auto_increment,
	user_name varchar(255) not null,
	constraint users_id_uindex
		unique (id)
);

alter table users
	add primary key (id);

