package com.example.College.Repository;

import com.example.College.Model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollegeRepository extends JpaRepository<College,Long> {
    @Query(value = "select * from College_College where college_is_deleted=false ",nativeQuery = true)
    List<College> getAllCollege();

    College findByCollegeIdAndCollegeIsDeleted(Long collegeId, boolean b);

    boolean existsByCollegeIdAndCollegeIsDeleted(Long collegeId, boolean b);
}
