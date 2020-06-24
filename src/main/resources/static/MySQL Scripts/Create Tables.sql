create schema if not exists `question-answer-platform` collate utf8_general_ci;

create table if not exists companies
(
	id int auto_increment
		primary key,
	company_name varchar(255) not null
);

create table if not exists subtopics
(
	id int auto_increment
		primary key,
	subtopic varchar(255) not null
);

create table if not exists tags
(
	id int auto_increment
		primary key,
	tag varchar(45) not null
);

create table if not exists topics
(
	id int auto_increment
		primary key,
	topic_name varchar(255) not null
);

create table if not exists topic_subtopic
(
	topics_id int not null,
	subtopics_id int not null,
	primary key (topics_id, subtopics_id),
	constraint fk_topics_has_subtopics_subtopics1
		foreign key (subtopics_id) references subtopics (id),
	constraint fk_topics_has_subtopics_topics1
		foreign key (topics_id) references topics (id)
);

create index fk_topics_has_subtopics_subtopics1_idx
	on topic_subtopic (subtopics_id);

create index fk_topics_has_subtopics_topics1_idx
	on topic_subtopic (topics_id);

create table if not exists users
(
	id int auto_increment
		primary key,
	user_name varchar(255) not null
);

create table if not exists questions
(
	id int auto_increment,
	question varchar(500) not null,
	likes int default 0 not null,
	users_id int not null,
	date date null,
	primary key (id, users_id),
	constraint fk_questions_users1
		foreign key (users_id) references users (id)
);

create table if not exists answers
(
	id int auto_increment,
	answer varchar(500) not null,
	likes int not null,
	questions_id int not null,
	users_id int not null,
	primary key (id, questions_id, users_id),
	constraint fk_answers_questions1
		foreign key (questions_id) references questions (id),
	constraint fk_answers_users1
		foreign key (users_id) references users (id)
);

create index fk_answers_questions1_idx
	on answers (questions_id);

create index fk_answers_users1_idx
	on answers (users_id);

create table if not exists comments
(
	id int auto_increment,
	comment varchar(500) not null,
	answers_id int not null,
	users_id int not null,
	date date not null,
	primary key (id, answers_id, users_id),
	constraint fk_comments_answers1
		foreign key (answers_id) references answers (id),
	constraint fk_comments_users1
		foreign key (users_id) references users (id)
);

create index fk_comments_answers1_idx
	on comments (answers_id);

create index fk_comments_users1_idx
	on comments (users_id);

create table if not exists question_company
(
	questions_id int not null,
	companies_id int not null,
	primary key (questions_id, companies_id),
	constraint fk_questions_has_companies_companies1
		foreign key (companies_id) references companies (id),
	constraint fk_questions_has_companies_questions1
		foreign key (questions_id) references questions (id)
);

create index fk_questions_has_companies_companies1_idx
	on question_company (companies_id);

create index fk_questions_has_companies_questions1_idx
	on question_company (questions_id);

create table if not exists question_subtopic
(
	questions_id int not null,
	subtopics_id int not null,
	primary key (questions_id, subtopics_id),
	constraint fk_questions_has_subtopics_questions1
		foreign key (questions_id) references questions (id),
	constraint fk_questions_has_subtopics_subtopics1
		foreign key (subtopics_id) references subtopics (id)
);

create index fk_questions_has_subtopics_questions1_idx
	on question_subtopic (questions_id);

create index fk_questions_has_subtopics_subtopics1_idx
	on question_subtopic (subtopics_id);

create table if not exists question_tag
(
	questions_id int not null,
	tags_id int not null,
	primary key (questions_id, tags_id),
	constraint fk_questions_has_tags_questions1
		foreign key (questions_id) references questions (id),
	constraint fk_questions_has_tags_tags1
		foreign key (tags_id) references tags (id)
);

create index fk_questions_has_tags_questions1_idx
	on question_tag (questions_id);

create index fk_questions_has_tags_tags1_idx
	on question_tag (tags_id);

create index fk_questions_users1_idx
	on questions (users_id);

