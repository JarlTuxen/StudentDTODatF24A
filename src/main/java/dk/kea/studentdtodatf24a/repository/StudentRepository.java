package dk.kea.studentdtodatf24a.repository;

import dk.kea.studentdtodatf24a.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
