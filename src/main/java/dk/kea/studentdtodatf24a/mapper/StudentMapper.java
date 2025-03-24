package dk.kea.studentdtodatf24a.mapper;

import dk.kea.studentdtodatf24a.dto.StudentRequestDTO;
import dk.kea.studentdtodatf24a.dto.StudentResponseDTO;
import dk.kea.studentdtodatf24a.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    //Konverter en Student entity til en StudentResponseDTO
    public StudentResponseDTO toDTO(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getBornDate(),
                student.getBornTime()
        );
    }

    //Konverter en StudentRequestDTO til en Student Entity for create
    public Student toEntity(StudentRequestDTO studentRequestDTO) {
        return new Student(
                studentRequestDTO.name(),
                studentRequestDTO.password(),
                studentRequestDTO.bornDate(),
                studentRequestDTO.bornTime()
        );
    }

    //Opdater en eksisterende Student Entity med data fra en StudentRequestDTO
    public void updateEntity(Student student, StudentRequestDTO studentRequestDTO) {
        student.setName(studentRequestDTO.name());
        student.setPassword(studentRequestDTO.password());
        student.setBornDate(studentRequestDTO.bornDate());
        student.setBornTime(studentRequestDTO.bornTime());
    }
}
