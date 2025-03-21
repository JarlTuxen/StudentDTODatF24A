package dk.kea.studentdtodatf24a.service;

import dk.kea.studentdtodatf24a.dto.StudentRequestDTO;
import dk.kea.studentdtodatf24a.dto.StudentResponseDTO;
import dk.kea.studentdtodatf24a.model.Student;
import dk.kea.studentdtodatf24a.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();

        // Using a for-loop to convert each Student to a StudentResponseDTO
        for (Student student : students) {
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                    student.getId(),
                    student.getName(),
                    student.getBornDate(),
                    student.getBornTime()
            );
            studentResponseDTOs.add(studentResponseDTO);
        }

        return studentResponseDTOs;
    }

    public StudentResponseDTO getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student studentResponse = optionalStudent.get();

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                studentResponse.getId(),
                studentResponse.getName(),
                studentResponse.getBornDate(),
                studentResponse.getBornTime()
        );

        return studentResponseDTO;

    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        //Student studentResponse = studentRepository.save(studentRequest);

        Student newStudent = new Student(
                studentRequestDTO.name(),
                studentRequestDTO.password(),
                studentRequestDTO.bornDate(),
                studentRequestDTO.bornTime()
        );

        Student savedStudent = studentRepository.save(newStudent);

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getBornDate(),
                savedStudent.getBornTime()
        );

        return studentResponseDTO;
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student student = optionalStudent.get();

        student.setName(studentRequestDTO.name());
        student.setPassword(studentRequestDTO.password());
        student.setBornDate(studentRequestDTO.bornDate());
        student.setBornTime(studentRequestDTO.bornTime());

        Student studentResponse = studentRepository.save(student);

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                studentResponse.getId(),
                studentResponse.getName(),
                studentResponse.getBornDate(),
                studentResponse.getBornTime()
        );

        return studentResponseDTO;
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            // Throw RuntimeException if student is not found
            throw new RuntimeException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }
}
