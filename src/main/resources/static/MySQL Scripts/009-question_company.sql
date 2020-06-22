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

