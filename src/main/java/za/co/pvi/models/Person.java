package za.co.pvi.models;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "user_id")
    private String userId;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    private  Integer age;
}
