package za.co.pvi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.pvi.models.Student;
import za.co.pvi.repositories.StudentRepository;
import za.co.pvi.services.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    Student student;

    @Override
    public Student save(Student student) {
//        saving to the database(student table)
        return studentRepository.save(student);
    }

    @Override
    public List<Student> saveAll(List<Student>  students) {
//        saving to the database(student table)
        return studentRepository.saveAll(students);
    }


    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student findById(Long id) {
        Optional<Student> studentById = studentRepository.findById(id);
        return studentById.orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
