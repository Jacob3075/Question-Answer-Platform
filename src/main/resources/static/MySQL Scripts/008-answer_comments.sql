create table if not exists answer_comments
(
	answers_id int not null,
	answer_comments_id int not null,
	primary key (answers_id, answer_comments_id),
	constraint fk_answers_has_answer_comments_answer_comments1
		foreign key (answer_comments_id) references comments (id),
	constraint fk_answers_has_answer_comments_answers1
		foreign key (answers_id) references answers (id)
);

create index fk_answers_has_answer_comments_answer_comments1_idx
	on answer_comments (answer_comments_id);

create index fk_answers_has_answer_comments_answers1_idx
	on answer_comments (answers_id);

