create table if not exists answers
(
    id     int auto_increment,
    answer varchar(500) not null,
    constraint answers_id_uindex
        unique (id)
);

alter table answers
    add primary key (id);

create table if not exists comments
(
    id      int auto_increment,
    comment varchar(500) null,
    constraint comments_id_uindex
        unique (id)
);

alter table comments
    add primary key (id);

create table if not exists answer_comments
(
    answers_id         int not null,
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

create table if not exists companies
(
    id           int auto_increment,
    company_name varchar(255) not null,
    constraint companies_id_uindex
        unique (id)
);

alter table companies
    add primary key (id);

create table if not exists questions
(
    id       int auto_increment,
    question varchar(500) not null,
    constraint questions_id_uindex
        unique (id)
);

alter table questions
    add primary key (id);

create table if not exists question_answers
(
    questions_id int not null,
    answers_id   int not null,
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

create table if not exists sub_topics
(
    id             int auto_increment,
    sub_topic_name varchar(255) null,
    constraint sub_topics_id_uindex
        unique (id)
);

alter table sub_topics
    add primary key (id);

create table if not exists question_sub_topic
(
    questions_id  int not null,
    sub_topics_id int not null,
    primary key (questions_id, sub_topics_id),
    constraint fk_questions_has_sub_topics_questions1
        foreign key (questions_id) references questions (id),
    constraint fk_questions_has_sub_topics_sub_topics1
        foreign key (sub_topics_id) references sub_topics (id)
);

create index fk_questions_has_sub_topics_questions1_idx
    on question_sub_topic (questions_id);

create index fk_questions_has_sub_topics_sub_topics1_idx
    on question_sub_topic (sub_topics_id);

create table if not exists tags
(
    id       int auto_increment,
    tag_name varchar(255) not null,
    constraint tags_id_uindex
        unique (id)
);

alter table tags
    add primary key (id);

create table if not exists question_tag
(
    questions_id int not null,
    tag_id       int not null,
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

create table if not exists topics
(
    id         int auto_increment,
    topic_name varchar(255) null,
    constraint topics_id_uindex
        unique (id)
);

alter table topics
    add primary key (id);

create table if not exists sub_topic_topic
(
    sub_topics_id int not null,
    topics_id     int not null,
    primary key (sub_topics_id, topics_id),
    constraint FKkq6clwq47anj61blka9r4y2qg
        foreign key (sub_topics_id) references topics (id)
            on update cascade on delete cascade,
    constraint FKp2kgsaa5m6qetn3h7m00x83m0
        foreign key (topics_id) references sub_topics (id)
            on update cascade on delete cascade,
    constraint fk_sub_topics_has_topics_topics1
        foreign key (topics_id) references topics (id)
);

create index fk_sub_topics_has_topics_sub_topics1_idx
    on sub_topic_topic (sub_topics_id);

create index fk_sub_topics_has_topics_topics1_idx
    on sub_topic_topic (topics_id);

create table if not exists users
(
    id        int auto_increment,
    user_name varchar(255) not null,
    constraint users_id_uindex
        unique (id)
);

alter table users
    add primary key (id);

create table if not exists liked_answers
(
    users_id   int not null,
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

create table if not exists liked_comments
(
    users_id           int not null,
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

create table if not exists liked_questions
(
    users_id     int not null,
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

create table if not exists user_answer_comments
(
    users_id           int not null,
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

create table if not exists user_answers
(
    users_id   int not null,
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

create table if not exists user_questions
(
    users_id     int not null,
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


