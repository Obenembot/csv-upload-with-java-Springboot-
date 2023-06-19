package za.co.pvi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.pvi.models.Student;
import za.co.pvi.models.dto.StudentDTO;
import za.co.pvi.services.StudentService;
import za.co.pvi.utils.CSVProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = {"http://localhost:4200"})
public class StudentResource {

    private Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);
    @Autowired
    StudentService studentService;

    @PostMapping("/student")
    public Student saveStudent(@RequestBody(required = false) Student student,
                               @RequestHeader(required = false, value = "X-USERNAME") String username,
                               @RequestHeader(required = false, value = "USER_ID") String userId) throws Exception {
        LOGGER.info("Rest request to save student : {}", student);
        if (student.getId() != null) {
            throw new Exception("Can not create a new student with id");
        }
//        if(!StringUtils.isEmpty(student.getUserId())){
        if (student.getUserId() != null) {
            String encode = Base64.getEncoder().encodeToString(student.getUserId().getBytes());
            student.setUserId(encode);
        }
//        student.setDecode(true);
        student.setCreatedBy(username);
        return studentService.save(student);
    }

    @PostMapping("/student/partial-create")
    public Student partialCreateStudent(@RequestBody StudentDTO studentDTO) throws Exception {
        LOGGER.info("Rest request to save partial student: {} ", studentDTO);
        if (studentDTO.getId() != null) {
            throw new Exception("Can not create a new student with id");
        }

//        Student student = new Student();
//        student.setEmail(studentDTO.getEmail());
//        student.setFirstName(studentDTO.getFirstName());
//        student.setLastName(studentDTO.getLastName());
        ObjectMapper objectMapper = new ObjectMapper();
        String userToConvert = objectMapper.writeValueAsString(studentDTO);
        Student student = objectMapper.readValue(userToConvert, Student.class);

        Student savedStudent = this.studentService.save(student);
        return savedStudent;
    }


    @PutMapping("/student")
    public Student updateStudent(@RequestBody Student student) throws Exception {
        LOGGER.info("Rest request to update student : {}", student);
        if (student.getId() == null) {
            throw new Exception("Can not update student with id null");
        }
        if (student.getUserId() != null && student.getDecode()) {
            byte[] decode1 = Base64.getUrlDecoder().decode(student.getUserId());
            String decodedValue = new String(decode1);
            student.setUserId(decodedValue);
        }
        return studentService.update(student);
    }

    @PutMapping("/student/partial-update")
    public Student partialUpdateStudent(@RequestBody StudentDTO studentDTO) throws Exception {
        LOGGER.info("Rest request to update student : {}", studentDTO);
        if (studentDTO.getId() == null) {
            throw new Exception("Can not update student with id null." + studentDTO.getId());
        }

        Student olderStudent = studentService.findById(studentDTO.getId());
        if (olderStudent == null) {
            throw new Exception("No student found with id." + studentDTO.getId());
        }
        olderStudent.setFirstName(studentDTO.getFirstName());
        olderStudent.setLastName(studentDTO.getLastName());
        olderStudent.setEmail(studentDTO.getEmail());

        return studentService.update(olderStudent);
    }


    @GetMapping("/student")
    List<Student> getAllStudents() {
        LOGGER.info("Rest request to get all students");
        return this.studentService.findAll();
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Long id) {
        LOGGER.info("Rest request get student by id: {}", id);
        return this.studentService.findById(id);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity deleteStudentById(@PathVariable Long id) {
        LOGGER.info("Rest request to delete by id : {}", id);
        this.studentService.deleteById(id);
//        return "student with id: " + id + " successfully deleted";
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/student")
    public String deleteStudent(@RequestBody Student student) {
        LOGGER.info("Rest request to delete student : {}", student);
        this.studentService.delete(student);
        return "student with id: " + student.getId() + " successfully deleted";
    }

    @PostMapping(value = "/student/bulk", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public void upload(@RequestPart("file") MultipartFile file) {
        List<Map<String, String>> list = CSVProcessor.ProcessFile(file);

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(list);
        Student[] students = gson.fromJson(jsonElement, Student[].class);

        List<Student> list1 = Arrays.asList(students);
        studentService.saveAll(list1);
    }

    @GetMapping("/student/download")
    public ResponseEntity searchDownloadAssets(Pageable pageable) {
        LOGGER.debug("REST request to search for a page of Assets for query {}", pageable);
        byte[] myFile = studentService.searchDownload(pageable);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + "assets.csv");
        return new ResponseEntity(myFile, httpHeaders, HttpStatus.OK);
    }
}
