package hogwarts.testhogwarts.repository;

import hogwarts.testhogwarts.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findAllByStudentId(Long studentId);
}
