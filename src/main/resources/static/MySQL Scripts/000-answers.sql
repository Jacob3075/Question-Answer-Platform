create table if not exists answers
(
	id int auto_increment,
	answer varchar(500) not null,
	constraint answers_id_uindex
		unique (id)
);

alter table answers
	add primary key (id);

