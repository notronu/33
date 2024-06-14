package hogwarts.testhogwarts.controller;


import hogwarts.testhogwarts.model.Faculty;
import hogwarts.testhogwarts.model.Student;
import hogwarts.testhogwarts.service.FacultyService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Faculty get(@RequestParam long id) {
        return facultyService.getFaculty(id);
    }

    @PostMapping

    public Faculty addFacultyInfo(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping

    public Faculty updateFacultyInfo(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping
    public boolean deleteFacultyInfo(@RequestParam Long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/byColorAndName")
    public Collection<Faculty> findFacultiesByColor(@RequestParam(required = false)String color, @RequestParam(required = false)String name) {

        if (color == null && name == null) {
            return facultyService.getAll();
        }
        return facultyService.findFacultiesByColorAndName(color, name);
    }
    @GetMapping("students")
    public List<Student> getStudentFaculty(@RequestParam long facultyId) {
        return facultyService.getFaculty(facultyId).getStudents();
    }

    @GetMapping("/longestName")
    public String getLongestName() {
        return facultyService.getLongestName();
    }
}
