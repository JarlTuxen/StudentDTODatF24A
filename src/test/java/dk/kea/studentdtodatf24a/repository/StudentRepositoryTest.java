package dk.kea.studentdtodatf24a.repository;

import dk.kea.studentdtodatf24a.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        //studentRepository.deleteAll();
        studentRepository.save(new Student("Klaus", "2345", LocalDate.of(2008, 5, 22), LocalTime.of(8, 30, 45)));
        studentRepository.save(new Student("Maksyn", "3456", LocalDate.of(2012, 7, 9), LocalTime.of(15, 20, 30)));
    }

    @Test
    void findAllTest() {
        List<Student> students = studentRepository.findAll();
        assertEquals(2, students.size());
    }

    @Test
    void findAllAgainTest() {
        List<Student> students = studentRepository.findAll();
        assertEquals(2, students.size());
    }
}