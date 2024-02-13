package com.example.College.Service;

import com.example.College.Model.College;
import com.example.College.Model.SaveRequest.SaveCollegeRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CollegeService {
    Object saveOrUpdateCollege(SaveCollegeRequest saveCompanyRequest);

    Object getCollegeById(Long collegeId) throws Exception;

    Object deleteById(Long collegeId) throws Exception;

    Object changeCollegeStatus(Long collegeId) throws Exception;

    List<College> getAllCollege();

    Object getStudentByCollegeId(Long collegeId, String studentName, String studentEmail, String studentMobileNo, String studentCode, Pageable pageable);

    Object getAllCollegeStudent(String collegeName, String studentName, String studentEmail, String studentMobileNo, String studentCode, String collegeWebsite, Pageable pageable);
}
