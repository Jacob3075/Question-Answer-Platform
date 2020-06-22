create table if not exists liked_answers
(
	users_id int not null,
	answers_id int not null,
	primary key (users_id, answers_id),
	constraint fk_users_has_answers_answers1
		foreign key (answers_id) references answers (id),
	constraint fk_users_has_answers_users1
		foreign key (users_id) references users (id)
);

create index fk_users_has_answers_answers1_idx
	on liked_answers (answers_id);

create index fk_users_has_answers_users1_idx
	on liked_answers (users_id);

