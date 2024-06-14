package hogwarts.testhogwarts.controller;

import hogwarts.testhogwarts.model.Faculty;
import hogwarts.testhogwarts.model.Student;
import hogwarts.testhogwarts.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController

@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Student getStudentInfo(@RequestParam Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping
    public Student addStudentInfo(@RequestBody Student Student) {
        return studentService.addStudent(Student);
    }

    @PutMapping
    public Student updateStudentInfo(@RequestBody Student Student) {
        return studentService.editStudent(Student);
    }

    @DeleteMapping()
    public boolean deleteStudentInfo(@RequestParam Long id) {
        return studentService.deleteStudent(id);
    }
    @GetMapping("/byAge")
    public Collection<Student> findByAgeBetween(@RequestParam(required = false) Integer min,
                                                @RequestParam(required = false) Integer max) {
        if (min == null && max == null) {
            return studentService.getAll();
        }
        return studentService.getByAgeBetween(min, max);
    }

    @GetMapping("faculty")
    public Faculty getStudentFaculty(@RequestParam long studentId) {
        return studentService.getStudent(studentId).getFaculty();
    }

    @GetMapping("/count")
    public int getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("/avg-age")
    public double getAvgAge() {
        return studentService.getAvgAge();
    }

    @GetMapping("/last")
    public Collection<Student> getLastStudents() {
        return studentService.getLastFive();
    }

    @GetMapping("/nameStartWithA")
    public Collection<String> getStudentNameStartsWithA() {
        return studentService.getNameStartWithA();
    }

    @GetMapping("/avg-age-stream")
    public double getAverageAgeStream() {
        return studentService.getAverageAgeStream();
    }
}
