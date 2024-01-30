package com.example.College.Service;

import com.example.College.Model.College;
import com.example.College.Model.SaveRequest.SaveCollegeRequest;

import java.util.List;

public interface CollegeService {
    Object saveOrUpdateCollege(SaveCollegeRequest saveCompanyRequest);

    Object getCollegeById(Long collegeId) throws Exception;

    Object deleteById(Long collegeId) throws Exception;

    Object changeCollegeStatus(Long collegeId) throws Exception;

    List<College> getAllCollege();
}
