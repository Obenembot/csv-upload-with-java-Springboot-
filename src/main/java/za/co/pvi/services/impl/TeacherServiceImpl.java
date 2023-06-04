package za.co.pvi.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.pvi.repositories.TeacherRepository;
import za.co.pvi.models.Teacher;
import za.co.pvi.services.TeacherService;
import za.co.pvi.web.TeacherResource;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private Logger LOG = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public Teacher save(Teacher teacher) {
        LOG.info("Request to save teacher : {}", teacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(Teacher teacher) {
        LOG.info("Request to delete teacher : {}", teacher);
        teacherRepository.delete(teacher);
    }

    @Override
    public void deleteById(Long id) {
        LOG.info("Request to delete teacher by id :{}", id);
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher findById(Long id) {
        Optional<Teacher> teacherById = teacherRepository.findById(id);
        return teacherById.orElse(null);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
