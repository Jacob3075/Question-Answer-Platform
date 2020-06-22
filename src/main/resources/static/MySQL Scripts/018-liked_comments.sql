create table if not exists liked_comments
(
	users_id int not null,
	answer_comments_id int not null,
	primary key (users_id, answer_comments_id),
	constraint fk_users_has_answer_comments_answer_comments1
		foreign key (answer_comments_id) references comments (id),
	constraint fk_users_has_answer_comments_users1
		foreign key (users_id) references users (id)
);

create index fk_users_has_answer_comments_answer_comments1_idx
	on liked_comments (answer_comments_id);

create index fk_users_has_answer_comments_users1_idx
	on liked_comments (users_id);

