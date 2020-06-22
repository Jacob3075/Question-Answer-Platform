create table if not exists user_answer_comments
(
	users_id int not null,
	answer_comments_id int not null,
	primary key (users_id, answer_comments_id),
	constraint fk_users_has_answer_comments_answer_comments2
		foreign key (answer_comments_id) references comments (id),
	constraint fk_users_has_answer_comments_users2
		foreign key (users_id) references users (id)
);

create index fk_users_has_answer_comments_answer_comments2_idx
	on user_answer_comments (answer_comments_id);

create index fk_users_has_answer_comments_users2_idx
	on user_answer_comments (users_id);

