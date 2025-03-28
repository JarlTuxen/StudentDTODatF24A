package dk.kea.studentdtodatf24a.service;

import dk.kea.studentdtodatf24a.dto.StudentRequestDTO;
import dk.kea.studentdtodatf24a.dto.StudentResponseDTO;
import dk.kea.studentdtodatf24a.mapper.StudentMapper;
import dk.kea.studentdtodatf24a.model.Student;
import dk.kea.studentdtodatf24a.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    // Constructor injection
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public List<StudentResponseDTO> getAllStudents() {
        /* List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();


         */
        // Using a for-loop to convert each Student to a StudentResponseDTO
       /* for (Student student : students) {

        */
            /*
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                    student.getId(),
                    student.getName(),
                    student.getBornDate(),
                    student.getBornTime()
            );

            studentResponseDTOs.add(studentResponseDTO);

             */
            /*
            studentResponseDTOs.add(studentMapper.toDTO(student));
        }

        return studentResponseDTOs;
    */
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                //.map(student -> studentMapper.toDTO(student))
                .collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(Long id) {
        /*Optional<Student> optionalStudent = studentRepository.findById(id);

        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student studentResponse = optionalStudent.get();
        */
        /*
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                studentResponse.getId(),
                studentResponse.getName(),
                studentResponse.getBornDate(),
                studentResponse.getBornTime()
        );
         */

        /*StudentResponseDTO studentResponseDTO = studentMapper.toDTO(studentResponse);
        return studentResponseDTO;*/

        Student responseStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));
        return studentMapper.toDTO(responseStudent);

    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        //Student studentResponse = studentRepository.save(studentRequest);

        /*
        Student newStudent = new Student(
                studentRequestDTO.name(),
                studentRequestDTO.password(),
                studentRequestDTO.bornDate(),
                studentRequestDTO.bornTime()
        );
         */
        Student newStudent = studentMapper.toEntity(studentRequestDTO);

        Student savedStudent = studentRepository.save(newStudent);

        /*
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getBornDate(),
                savedStudent.getBornTime()
        );
         */
        //StudentResponseDTO studentResponseDTO = studentMapper.toDTO(savedStudent);
        //return studentResponseDTO;

        return studentMapper.toDTO(savedStudent);
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        /*Optional<Student> optionalStudent = studentRepository.findById(id);
        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student student = optionalStudent.get();

         */
        /*
        student.setName(studentRequestDTO.name());
        student.setPassword(studentRequestDTO.password());
        student.setBornDate(studentRequestDTO.bornDate());
        student.setBornTime(studentRequestDTO.bornTime());
         */
        //studentMapper.updateEntity(student, studentRequestDTO);


        /*
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                studentResponse.getId(),
                studentResponse.getName(),
                studentResponse.getBornDate(),
                studentResponse.getBornTime()
        );
         */
        /*StudentResponseDTO studentResponseDTO = studentMapper.toDTO(studentResponse);

        return studentResponseDTO;*/

        //find studerende med id og pak ud hvis fundet
        Student student = studentRepository.findById(id)
                //kast fejl hvis ikke fundet
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

        //opdater studerende med requestDTO
        studentMapper.updateEntity(student, studentRequestDTO);

        //gem opdaterede studerende
        Student studentResponse = studentRepository.save(student);

        //lav studerende om til DTO og returner
        return studentMapper.toDTO(studentResponse);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            // Throw RuntimeException if student is not found
            throw new RuntimeException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }
}
