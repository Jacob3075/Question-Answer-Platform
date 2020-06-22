create table if not exists sub_topics
(
	id int auto_increment,
	sub_topic_name varchar(255) null,
	constraint sub_topics_id_uindex
		unique (id)
);

alter table sub_topics
	add primary key (id);

