package za.co.pvi.services;

import org.springframework.data.domain.Pageable;
import za.co.pvi.models.Student;

import java.util.List;

public interface StudentService {

    Student save(Student student);

    List<Student> saveAll(List<Student> students);


    Student update(Student student);

    void delete(Student student);

    void deleteById(Long id);

    Student findById(Long id);

    List<Student> findAll();

    byte[] searchDownload(Pageable pageable);
}
