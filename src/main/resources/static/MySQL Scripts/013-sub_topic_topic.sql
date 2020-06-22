create table if not exists sub_topic_topic
(
	sub_topics_id int not null,
	topics_id int not null,
	primary key (sub_topics_id, topics_id),
	constraint FKkq6clwq47anj61blka9r4y2qg
		foreign key (sub_topics_id) references topics (id)
			on update cascade on delete cascade,
	constraint FKp2kgsaa5m6qetn3h7m00x83m0
		foreign key (topics_id) references sub_topics (id)
			on update cascade on delete cascade,
	constraint fk_sub_topics_has_topics_topics1
		foreign key (topics_id) references topics (id)
);

create index fk_sub_topics_has_topics_sub_topics1_idx
	on sub_topic_topic (sub_topics_id);

create index fk_sub_topics_has_topics_topics1_idx
	on sub_topic_topic (topics_id);

