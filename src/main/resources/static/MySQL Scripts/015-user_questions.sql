create table if not exists user_questions
(
	users_id int not null,
	questions_id int not null,
	primary key (users_id, questions_id),
	constraint fk_users_has_questions_questions2
		foreign key (questions_id) references questions (id),
	constraint fk_users_has_questions_users2
		foreign key (users_id) references users (id)
);

create index fk_users_has_questions_questions2_idx
	on user_questions (questions_id);

create index fk_users_has_questions_users2_idx
	on user_questions (users_id);

