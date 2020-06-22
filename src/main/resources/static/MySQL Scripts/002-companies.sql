create table if not exists companies
(
	id int auto_increment,
	company_name varchar(255) not null,
	constraint companies_id_uindex
		unique (id)
);

alter table companies
	add primary key (id);

