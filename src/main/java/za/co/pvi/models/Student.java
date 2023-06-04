package za.co.pvi.models;

import lombok.Data;

import javax.persistence.*;

@Data

@Entity
@Table(name = "students")
public class Student extends Person {

    private Boolean decode;

    public Student() {

    }

}
