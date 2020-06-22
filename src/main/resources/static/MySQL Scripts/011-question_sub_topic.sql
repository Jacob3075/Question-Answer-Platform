create table if not exists question_sub_topic
(
	questions_id int not null,
	sub_topics_id int not null,
	primary key (questions_id, sub_topics_id),
	constraint fk_questions_has_sub_topics_questions1
		foreign key (questions_id) references questions (id),
	constraint fk_questions_has_sub_topics_sub_topics1
		foreign key (sub_topics_id) references sub_topics (id)
);

create index fk_questions_has_sub_topics_questions1_idx
	on question_sub_topic (questions_id);

create index fk_questions_has_sub_topics_sub_topics1_idx
	on question_sub_topic (sub_topics_id);

