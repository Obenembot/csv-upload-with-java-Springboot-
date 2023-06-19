package za.co.pvi.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.pvi.models.Student;
import za.co.pvi.repositories.StudentRepository;
import za.co.pvi.services.StudentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    StudentRepository studentRepository;

    Student student;

    @Override
    public Student save(Student student) {
//        saving to the database(student table)
        return studentRepository.save(student);
    }

    @Override
    public List<Student> saveAll(List<Student> students) {
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

    @Override
    public byte[] searchDownload(Pageable pageable) {
        return this.generateCsv(studentRepository.findAll(pageable).getContent());
    }

    private byte[] generateCsv(List<Student> assets) {
        Gson g = new Gson();
        byte[] file = null;
        List<Student> toCSV = assets;
//        If the assets is too large or you only want a few fields to be downloaded, you can construct the toCSV object.
        if (!assets.isEmpty()) {
            try {
                JsonNode jsonNode = new ObjectMapper().readTree(g.toJson(toCSV));
                CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
                JsonNode firstObject = jsonNode.elements().next();
                firstObject.fieldNames().forEachRemaining(fieldName -> {
                    csvSchemaBuilder.addColumn(fieldName);
                });
                CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

                CsvMapper csvMapper = new CsvMapper();
                file = csvMapper.writerFor(JsonNode.class)
                        .with(csvSchema)
                        .writeValueAsBytes(jsonNode);
            } catch (JsonProcessingException jpe) {
                LOGGER.info(jpe.getMessage());
            } catch (IOException ioe) {
                LOGGER.info(ioe.getMessage());
            }
        }

        return file;
    }
}
