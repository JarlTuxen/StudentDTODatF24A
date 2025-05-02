package dk.kea.studentdtodatf24a.service;

import dk.kea.studentdtodatf24a.dto.StudentResponseDTO;
import dk.kea.studentdtodatf24a.mapper.StudentMapper;
import dk.kea.studentdtodatf24a.model.Student;
import dk.kea.studentdtodatf24a.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @Mock
    private StudentRepository mockedStudentRepository;

    StudentService studentService;

    @BeforeEach
    void setUp() {
        //studentRepository.save(new Student("Anders", "2345", LocalDate.of(2008, 5, 22), LocalTime.of(8, 30, 45)));
        //studentRepository.save(new Student("Lina", "3456", LocalDate.of(2012, 7, 9), LocalTime.of(15, 20, 30)));
        List<Student> students = List.of(
                new Student(1L, "Anders", "123", LocalDate.of(2008, 5, 22), LocalTime.of(8, 30, 45)),
                new Student(2L, "Lina","Hemmeligt", LocalDate.of(2012, 7, 9), LocalTime.of(15, 20, 30))
        );

        //findAll skal give liste af responseDto'er
        Mockito.when(mockedStudentRepository.findAll()).thenReturn(students);

        studentService = new StudentService(mockedStudentRepository, new StudentMapper());
    }

    @Test
    void getAllStudentsTest() {
        //Arrange
        //setup af Mock i BeforeEach

        //Act
        List<StudentResponseDTO> studentResponseDTOS = studentService.getAllStudents();

        //Assert
        assertNotNull(studentResponseDTOS);
        assertEquals(studentResponseDTOS.size(), 2);
        assertEquals("Anders", studentResponseDTOS.get(0).name());
    }

    @Test
    void getStudentByIdTest() {
    }

    @Test
    void createStudentTest() {
    }

    @Test
    void updateStudentTest() {
    }

    @Test
    void deleteStudentTest() {
    }
}