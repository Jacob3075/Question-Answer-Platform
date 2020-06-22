create table if not exists topics
(
	id int auto_increment,
	topic_name varchar(255) null,
	constraint topics_id_uindex
		unique (id)
);

alter table topics
	add primary key (id);

