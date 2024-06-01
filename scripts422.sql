CREATE TABLE public.cars (
	cars_id int4 NULL,
	cars_brand varchar NULL,
	cars_model varchar NULL,
	cars_price numeric NULL
);

alter table cars alter column cars_brand set not null;
alter table cars add constraint cars_model_unique unique (cars_model);
alter table cars add constraint cars_price_constraint check (cars_price>0);

alter table cars add primary key (cars_id);



CREATE TABLE public.person (
	person_id int NULL,
	person_name varchar NULL,
	person_age int NULL,
	person_driver_license boolean NULL
);

alter table person alter column person_name set not null;
alter table person add constraint person_age_constraint check (person_age>0);
alter table person alter column person_driver_license set not null;


ALTER TABLE cars ADD FOREIGN KEY (person_id) REFERENCES person (person_id);