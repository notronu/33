package hogwarts.testhogwarts.service;
import hogwarts.testhogwarts.exception.RecordNotFoundException;
import hogwarts.testhogwarts.model.Student;
import hogwarts.testhogwarts.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void shouldGetStudent() {
        long id = 1L;
        Student student = new Student();
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.of(student));

        Student foundStudent = studentService.getStudent(id);

        verify(studentRepository).findById(id);
        assertThat(foundStudent).isEqualTo(student);
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        long id = 1L;
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> studentService.getStudent(id)).isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    void shouldDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));

        boolean result = studentService.deleteStudent(1L);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = studentService.deleteStudent(1L);

        assertThat(result).isFalse();
    }

    @Test
    void shouldEditStudent() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.editStudent(student);

        assertThat(result).isEqualTo(student);
    }

    @Test
    void shouldNotEditFaculty() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Student result = studentService.editStudent(student);
    }
}
