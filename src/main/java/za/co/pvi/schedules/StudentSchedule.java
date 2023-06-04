package za.co.pvi.schedules;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import za.co.pvi.models.Student;
import za.co.pvi.repositories.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Component
@Slf4j
public class StudentSchedule {

@Autowired
    private StudentRepository studentRepository;


    @Scheduled(fixedDelay = 1000 * 10)
//    @Scheduled(cron = "* * * * * *") //check cron job for what time you want
    public void updateAllStudentWithCreatedByNull(){
      // List<Student> findByFirst = studentRepository.findByFirst("lmatthaus8@usatoday.com");
        List<Student> findAllWithCreatedByNull = studentRepository.findAllWithCreatedByNull();

        if(!findAllWithCreatedByNull.isEmpty()){
            findAllWithCreatedByNull.stream().forEach( student -> {

                student.setCreatedBy("System");
                studentRepository.save(student);
            });
        }
    }
}
