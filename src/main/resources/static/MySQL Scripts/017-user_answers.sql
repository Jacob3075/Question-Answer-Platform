create table if not exists user_answers
(
	users_id int not null,
	answers_id int not null,
	primary key (users_id, answers_id),
	constraint fk_users_has_answers_answers2
		foreign key (answers_id) references answers (id),
	constraint fk_users_has_answers_users2
		foreign key (users_id) references users (id)
);

create index fk_users_has_answers_answers2_idx
	on user_answers (answers_id);

create index fk_users_has_answers_users2_idx
	on user_answers (users_id);

