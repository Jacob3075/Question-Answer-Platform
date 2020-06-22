create table if not exists questions
(
	id int auto_increment,
	question varchar(500) not null,
	constraint questions_id_uindex
		unique (id)
);

alter table questions
	add primary key (id);

