package za.co.pvi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import za.co.pvi.models.Student;
import za.co.pvi.models.Teacher;

@RequestMapping("/api")
@RestController
//@Controller
public class TestEndpoints {

//    https://start.spring.io/
//    http://localhost:8080/
//    http://localhost:8080/api/greetings
//    http://localhost:8080/api/greetings

    /*
    Get,Post,Delete,PUt
     */


   @GetMapping("/greetings")
    public String getName(){
        return "Hi How are you!";
    }
    @GetMapping("/greetings/{name}")
    public String getName(@PathVariable String name){
        return "Hi How are you! "+ name;
    }

    @GetMapping("/greetings/{name}/{surname}")
    public String getName(@PathVariable String name, @PathVariable String surname){
        return "Hi How are you! "+ name +' '+ surname;
    }


    @PostMapping("/saveStudent")
    public Student saveStudent(@RequestBody Student student){
        System.out.println("create: "+ student);
        return  student;
    }

    @PutMapping("/saveStudent")
    public Student updateStudent(@RequestBody Student student){
       // System.out.println("update: "+ student);
        return  student;
    }


}
