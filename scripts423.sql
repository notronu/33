select * from student s join public.faculty f on f.id = s.faculty_id;
insert into faculty (color, "name") values ('black', 'magic');
update student set faculty_id = 50;

select * from student s join avatar a on s.id = a.student_id where a.student_id is not null;