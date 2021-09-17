drop database if exists homework_3;
create database homework_3;
create table companies (
	id int not null auto_increment,
    `name` varchar(45) not null,
    registration_country varchar(45) not null,
	primary key (id));
create table customers (
	id int not null auto_increment,
    `name` varchar(45) not null,
    owner_name varchar(45) not null,
	primary key (id),
	unique key (`name`));
create table developers (
	id int not null auto_increment,
    `name` varchar(45) not null,
    age int not null,
    gender varchar(45) not null,
    email varchar(45) not null,
    salary int not null,
    company_id int not null,
	primary key (id),
	unique key (email),
	foreign key (company_id) references companies (id));
create table projects (
	id int not null auto_increment,
    `name` varchar(45) not null,
    cost varchar(45) not null,
    creation_date date not null,
    customer_id int not null,
    company_id int not null,
    primary key (id),
    unique key (`name`),
    foreign key (customer_id) references customers (id),
    foreign key (company_id) references companies (id));
create table skills (
	id int not null auto_increment,
    `name` varchar(45) not null,
    `level` varchar(45) not null,
    primary key (id));
create table dev_and_projects (
	developer_id int not null,
    project_id int not null,
    foreign key (developer_id) references developers (id),
    foreign key (project_id) references projects (id));
create table dev_and_skills (
	developer_id int not null,
    skill_id int not null,
    foreign key (developer_id) references developers (id),
    foreign key (skill_id) references skills (id));