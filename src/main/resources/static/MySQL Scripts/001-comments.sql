create table if not exists comments
(
	id int auto_increment,
	comment varchar(500) null,
	constraint comments_id_uindex
		unique (id)
);

alter table comments
	add primary key (id);

