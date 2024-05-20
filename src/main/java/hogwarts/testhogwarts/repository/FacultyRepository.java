package hogwarts.testhogwarts.repository;

import hogwarts.testhogwarts.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findAllByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

}
