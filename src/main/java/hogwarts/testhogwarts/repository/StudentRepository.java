package hogwarts.testhogwarts.repository;

import hogwarts.testhogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAgeBetween(int min, int max);

}
