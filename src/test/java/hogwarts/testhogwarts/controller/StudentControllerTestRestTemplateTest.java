package hogwarts.testhogwarts.controller;

import hogwarts.testhogwarts.model.Student;
import org.h2.util.TempFileDeleter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbc, "STUDENT");
    }

    @Test
    public void getStudentInfoTest() throws Exception {

        Student student = new Student(1L, "lora", 9);

        ResponseEntity<Student> postResponse = restTemplate.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        var result = restTemplate.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getName()).isEqualTo("lora");
        assertThat(result.getAge()).isEqualTo(9);

        ResponseEntity<Student> resultAfterDelete = restTemplate.exchange("/student?id=-1", HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void deleteStudentTest() {
        Student student = new Student(1L, "lora", 9);

        ResponseEntity<Student> postResponse = restTemplate.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        var result = restTemplate.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getName()).isEqualTo("lora");
        assertThat(result.getAge()).isEqualTo(9);

        restTemplate.delete("/student?id=" + addedStudent.getId());

        ResponseEntity<Student> resultAfterDelete = restTemplate.exchange("/student?id=-1", HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);

    }

    @Test
    public void updateStudentTest() {
        Student student = new Student(1L, "lora", 9);

        ResponseEntity<Student> postResponse = restTemplate.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        addedStudent.setName("marina");
        addedStudent.setAge(20);
        restTemplate.put("/student?id=" + addedStudent.getId(), addedStudent);

        var result = restTemplate.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getName()).isEqualTo("marina");
        assertThat(result.getAge()).isEqualTo(20);

    }

    @Test
    public void getStudentsByAgeTest() {
        var s1 = restTemplate.postForEntity("/student", new Student(null, "lora1", 9), Student.class).getBody();
        var s2 = restTemplate.postForEntity("/student", new Student(null, "lora2", 10), Student.class).getBody();
        var s3 = restTemplate.postForEntity("/student", new Student(null, "lora3", 11), Student.class).getBody();
        var s4 = restTemplate.postForEntity("/student", new Student(null, "lora4", 12), Student.class).getBody();

        var expected = restTemplate.getForObject("/student/byAge?min=9&max=12", Student[].class);
        assertThat(expected.length).isEqualTo(4);
        assertThat(expected).containsExactlyInAnyOrder(s1, s2, s3, s4);

    }

    @Test
    public void getAllStudentsTest() {
        var s1 = restTemplate.postForEntity("/student", new Student(null, "lora1", 9), Student.class).getBody();
        var s2 = restTemplate.postForEntity("/student", new Student(null, "lora2", 10), Student.class).getBody();
        var s3 = restTemplate.postForEntity("/student", new Student(null, "lora3", 11), Student.class).getBody();
        var s4 = restTemplate.postForEntity("/student", new Student(null, "lora4", 12), Student.class).getBody();

        var expected = restTemplate.getForObject("/student/byAge", Student[].class);
        assertThat(expected.length).isEqualTo(4);
        assertThat(expected).containsExactlyInAnyOrder(s1, s2, s3, s4);
    }
}
