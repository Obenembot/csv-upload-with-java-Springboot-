package za.co.pvi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.pvi.models.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query( value = "select * FROM pvi_registration_local.students WHERE  created_by is null", nativeQuery = true)
    List<Student> findAllWithCreatedByNull();

    @Query( value = "select * FROM pvi_registration_local.students WHERE  first_name = ?1", nativeQuery = true)
    List<Student> findByFirst(String firstNAme);

    List<Student> findByEmail(String email);

    List<Student> findByEmailAndFirstName(String email, String firstName);

}
