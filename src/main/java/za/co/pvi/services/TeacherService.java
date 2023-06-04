package za.co.pvi.services;

import za.co.pvi.models.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Teacher teacher);


    void deleteById(Long id);

    Teacher findById(Long id);

    List<Teacher> findAll();
}
