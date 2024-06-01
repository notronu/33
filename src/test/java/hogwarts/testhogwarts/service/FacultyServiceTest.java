package hogwarts.testhogwarts.service;

import hogwarts.testhogwarts.exception.RecordNotFoundException;
import hogwarts.testhogwarts.model.Faculty;
import hogwarts.testhogwarts.repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepository;
    private FacultyService facultyService;


    @BeforeEach
    public void setUp() {
        facultyService = new FacultyService(facultyRepository);
    }

    @Test
    void shouldGetFaculty() {
        // given
        long id = 1L;
        Faculty faculty = new Faculty();
        when(facultyRepository.findById(id)).thenReturn(java.util.Optional.of(faculty));

        // when
        Faculty foundFaculty = facultyService.getFaculty(id);

        // then
        verify(facultyRepository).findById(id);
        assertThat(foundFaculty).isEqualTo(faculty);
    }

    @Test
    void shouldThrowExceptionWhenFacultyNotFound() {
        // given
        long id = 1L;
        when(facultyRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> facultyService.getFaculty(id)).isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    void shouldDeleteFaculty() {

        Faculty actual = new Faculty(1L,"Grif","orange");
        actual.setId(1L);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty()));

        boolean result = facultyService.deleteFaculty(1L);

        assertThat(result).isTrue();
    }

    @Test
    void shouldEditFaculty() {

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty()));
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty result = facultyService.editFaculty(faculty);


        assertThat(result).isEqualTo(faculty);
    }

    @Test
    void shouldNotEditFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        Faculty result = facultyService.editFaculty(faculty);
    }
}
