package hogwarts.testhogwarts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hogwarts.testhogwarts.model.Faculty;
import hogwarts.testhogwarts.model.Student;
import hogwarts.testhogwarts.repository.StudentRepository;
import hogwarts.testhogwarts.service.AvatarService;
import hogwarts.testhogwarts.service.FacultyService;
import hogwarts.testhogwarts.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class StudentControllerTestWebMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testGet() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student", 19)));
        mockMvc.perform(MockMvcRequestBuilders.get("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_student"))
                .andExpect(jsonPath("$.age").value(19));

    }

    @Test
    void testUpdate() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student", 19)));
        Student student = new Student(1L, "marina", 21);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/student?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("marina"))
                .andExpect(jsonPath("$.age").value(21));
    }

    @Test
    void testDelete() throws Exception {
        when(studentRepository.findById(2L)).thenReturn(Optional.of(new Student(1L, "test_student", 19)));
        mockMvc.perform(MockMvcRequestBuilders.delete("/student?id=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testAdd() throws Exception {
        when(studentRepository.save(any(Student.class))).then(invocationOnMock -> {
            Student input = invocationOnMock.getArgument(0, Student.class);
            Student f = new Student();
            f.setId(100L);
            f.setAge(input.getAge());
            f.setName(input.getName());
            return f;
        });

        Student student = new Student(null, "kira", 25);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("kira"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void testStudentByAge() throws Exception {
        when(studentRepository.findAllByAgeBetween(anyInt(), anyInt()))
                .thenReturn(List.of(
                        new Student(1L, "marina", 21),
                        new Student(2L, "kira", 25)));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/byAge?min=19&max=23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("marina"))
                .andExpect(jsonPath("$[0].age").value(21));

    }

    @Test
    void testGetStudents() throws Exception {
        Student f1 = new Student(1L,"kira", 25);
        f1.setFaculty(new Faculty(1L,"sliz","green"));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(f1));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/faculty?studentId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sliz"))
                .andExpect(jsonPath("$.color").value("green"));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/faculty?studentId="))
                .andExpect(status().is(400));
    }
}
