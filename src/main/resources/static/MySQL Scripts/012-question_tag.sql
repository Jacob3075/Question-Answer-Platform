create table if not exists question_tag
(
	questions_id int not null,
	tag_id int not null,
	primary key (questions_id, tag_id),
	constraint fk_questions_has_tag_questions1
		foreign key (questions_id) references questions (id),
	constraint fk_questions_has_tag_tag1
		foreign key (tag_id) references tags (id)
);

create index fk_questions_has_tag_questions1_idx
	on question_tag (questions_id);

create index fk_questions_has_tag_tag1_idx
	on question_tag (tag_id);

