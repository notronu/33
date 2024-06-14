package hogwarts.testhogwarts.service;

import hogwarts.testhogwarts.exception.RecordNotFoundException;
import hogwarts.testhogwarts.model.Student;
import hogwarts.testhogwarts.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("The student has been added to the application");
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.info("Received the student of ID");
        return studentRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }


    public boolean deleteStudent(long id) {
        logger.info("The student of ID has been deleted");
        return studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public Student editStudent(Student student) {
        logger.info("The student has been updated");
        return studentRepository.findById(student.getId())
                .map(entity -> studentRepository.save(student))
                .orElse(null);
    }

    public Collection<Student> getByAgeBetween(int min, int max) {
        logger.info("A student is received between the maximum and minimum years");
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        logger.info("All students have been received");
        return studentRepository.findAll();
    }

    public Collection<Student> getByAgeStudents(int age) {
        logger.info("Received a student by age");
        return studentRepository.findByAge(age);
    }

    public int getStudentCount() {
        logger.info("The number of all students in the school is obtained");
        return studentRepository.countStudents();
    }

    public double getAvgAge() {
        logger.info("The average age of students was obtained");
        return studentRepository.avgAge();
    }

    public Collection<Student> getLastFive() {
        logger.info("Only the last five students have been received");
        return studentRepository.getLastFive();
    }

    public Collection<String> getNameStartWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name->name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAgeStream() {
        return studentRepository.findAll().stream()
                .mapToDouble(s->s.getAge())
                .average()
                .orElse(0);
    }
}

