package za.co.pvi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.pvi.models.Teacher;
import za.co.pvi.services.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = {"http://localhost:4200"})
public class TeacherResource {

    private Logger LOGGER = LoggerFactory.getLogger(TeacherResource.class);

    @Autowired
    TeacherService teacherService;

    @PostMapping("/teacher")
    public Teacher saveTeacher(@RequestBody Teacher teacher) throws Exception {
        LOGGER.info("Rest request to save teacher : {}", teacher);
        if (teacher.getId() != null) {
            throw new Exception("Cannot create a new teacher with the id");
        }
        return teacherService.save(teacher);
    }

    @PutMapping("/teacher")
    public Teacher updateTeacher(@RequestBody Teacher teacher) throws Exception {
        LOGGER.info("Rest request to update teacher : {}", teacher);
        if (teacher.getId() == null) {
            throw new Exception("Cannot update something that does not exist");

        }
        return teacherService.update(teacher);
    }


    @GetMapping("/teacher/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        System.out.println("Rest request to get a teacher with id " + id);
        LOGGER.info("Request to get a teacher with id: {} ", id);
        return this.teacherService.findById(id);
    }

    @GetMapping("/teacher")
    List<Teacher> getAllTeachers() {
        return this.teacherService.findAll();
    }


    @DeleteMapping("/teacher/{id}")
    public String deleteTeacherById(@PathVariable Long id) {
        LOGGER.info("rest request to delete teacher by id : {}", id);
        this.teacherService.deleteById(id);
        return "teacher with id" + id + "Successful deleted";
    }


    @DeleteMapping("/teacher")
    public String deleteTeacher(@RequestBody Teacher teacher) {
        LOGGER.info("rest request to delete teachers : {}", teacher);
        this.teacherService.delete(teacher);
        return "teacher with id" + teacher.getId() + "Successful deleted";

    }


}



