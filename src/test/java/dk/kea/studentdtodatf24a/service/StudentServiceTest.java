package dk.kea.studentdtodatf24a.service;

import dk.kea.studentdtodatf24a.dto.StudentRequestDTO;
import dk.kea.studentdtodatf24a.dto.StudentResponseDTO;
import dk.kea.studentdtodatf24a.mapper.StudentMapper;
import dk.kea.studentdtodatf24a.model.Student;
import dk.kea.studentdtodatf24a.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class StudentServiceTest {

    @Mock
    private StudentRepository mockedStudentRepository;

    StudentService studentService;

    @BeforeEach
    void setUp() {
        //studentRepository.save(new Student("Anders", "2345", LocalDate.of(2008, 5, 22), LocalTime.of(8, 30, 45)));
        //studentRepository.save(new Student("Lina", "3456", LocalDate.of(2012, 7, 9), LocalTime.of(15, 20, 30)));

        Student s1 = new Student(1L, "Anders", "123", LocalDate.of(2008, 5, 22), LocalTime.of(8, 30, 45));
        Student s2 = new Student(2L, "Lina","Hemmeligt", LocalDate.of(2012, 7, 9), LocalTime.of(15, 20, 30));
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);

        //findAll skal give liste af responseDto'er
        Mockito.when(mockedStudentRepository.findAll()).thenReturn(students);

        //findById giver studerende på id=1 og empty på id=42
        Mockito.when(mockedStudentRepository.findById(1L)).thenReturn(Optional.of(s1));
        Mockito.when(mockedStudentRepository.findById(42L)).thenReturn(Optional.empty());

        //deleteById giver empty hvis id = 42 - doThrow bruges, da deleteById er void
        //Mockito.when(mockedStudentRepository.deleteById(42)).thenThrow(new StudentNotFoundException("Student not found with id: 42"));
        doThrow(new RuntimeException("Student not found with id: 42")).when(mockedStudentRepository).deleteById(42L);

        // Define the behavior using thenAnswer
        // The student passed in save, can be read from arguments in the InvocationOnMock object
        Mockito.when(mockedStudentRepository.save(ArgumentMatchers.any(Student.class))).thenAnswer(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocation) throws Throwable {
                // Extract the student object passed as an argument to the save method
                Object[] arguments = invocation.getArguments();
                if (arguments.length > 0 && arguments[0] instanceof Student) {
                    Student studentToSave = (Student) arguments[0];
                    //er id = 0, så simuler create - er id sat så simuler opdater
                    if (studentToSave.getId()==null) {
                        //create - repository skal returnere studentobject med næste ledige id = 3
                        studentToSave.setId(3L);
                    }
                    return studentToSave;
                } else {
                    // Handle the case where the argument is not a Student (optional)
                    throw new IllegalArgumentException("Invalid argument type");
                }
            }
        });

        //inject mocked repository
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
        //Arrange Mock is handled in @BeforeEach
        // Act
        StudentResponseDTO studentDTO = studentService.getStudentById(1L);
        //Assert
        assertEquals("Anders", studentDTO.name());
        //Act & Assert for non-existing student
        assertThrows(RuntimeException.class, () -> studentService.getStudentById(42L));
    }

    @Test
    void createStudentTest() {
        //Arrange Mock is handled in @BeforeEach
        //Arrange & Act
        StudentResponseDTO resultStudentDTO = studentService.createStudent(
                new StudentRequestDTO(
                        "Hugo",
                        "Secret",
                        LocalDate.of(2000,1,1),
                        LocalTime.of(0, 0, 1)
                ));
        //Assert
        assertEquals("Hugo", resultStudentDTO.name());
        assertEquals(3L, resultStudentDTO.id());
    }

    @Test
    void updateStudentTest() {
        //Arrange Mock is handled in @BeforeEach
        //Arrange
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO(
                "Hugo",
                "Secret",
                LocalDate.of(2000,1,1),
                LocalTime.of(0, 0, 1));
        //Act
        StudentResponseDTO resultStudentDTO = studentService.updateStudent(1L,  studentRequestDTO);
        //Assert
        assertEquals(1, resultStudentDTO.id());
        assertEquals("Hugo", resultStudentDTO.name());
        //Act & Assert for non-existing student
        assertThrows(RuntimeException.class, () -> studentService.updateStudent(42L, studentRequestDTO));
    }

    @Test
    void deleteStudentTest() {
        //Arrange Mock is handled in @BeforeEach
        //Act & Assert
        assertThrows(RuntimeException.class, () -> studentService.deleteStudent(42L));
    }
}