create table if not exists tags
(
	id int auto_increment,
	tag_name varchar(255) not null,
	constraint tags_id_uindex
		unique (id)
);

alter table tags
	add primary key (id);

