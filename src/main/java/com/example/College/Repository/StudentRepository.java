package com.example.College.Repository;

import com.example.College.Model.College;
import com.example.College.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query(value = "select * from College_Student where student_is_deleted=false ",nativeQuery = true)
    List<Student> getAllStudent();
}
