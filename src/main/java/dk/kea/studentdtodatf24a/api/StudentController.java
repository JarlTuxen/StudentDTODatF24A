package dk.kea.studentdtodatf24a.api;

import dk.kea.studentdtodatf24a.dto.StudentRequestDTO;
import dk.kea.studentdtodatf24a.dto.StudentResponseDTO;
import dk.kea.studentdtodatf24a.model.Student;
import dk.kea.studentdtodatf24a.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        try {
            StudentResponseDTO student = studentService.getStudentById(id);
            //return new ResponseEntity<>(student, HttpStatus.OK);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO studentRequest) {
        StudentResponseDTO newStudent = studentService.createStudent(studentRequest);
        //return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            StudentResponseDTO updatedStudent = studentService.updateStudent(id, studentRequestDTO);
            //return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
