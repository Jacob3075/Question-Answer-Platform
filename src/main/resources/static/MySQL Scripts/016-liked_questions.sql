create table if not exists liked_questions
(
	users_id int not null,
	questions_id int not null,
	primary key (users_id, questions_id),
	constraint fk_users_has_questions_questions1
		foreign key (questions_id) references questions (id),
	constraint fk_users_has_questions_users1
		foreign key (users_id) references users (id)
);

create index fk_users_has_questions_questions1_idx
	on liked_questions (questions_id);

create index fk_users_has_questions_users1_idx
	on liked_questions (users_id);

