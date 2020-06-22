create table if not exists question_answers
(
	questions_id int not null,
	answers_id int not null,
	primary key (questions_id, answers_id),
	constraint fk_questions_has_answers_answers1
		foreign key (answers_id) references answers (id),
	constraint fk_questions_has_answers_questions1
		foreign key (questions_id) references questions (id)
);

create index fk_questions_has_answers_answers1_idx
	on question_answers (answers_id);

create index fk_questions_has_answers_questions1_idx
	on question_answers (questions_id);

