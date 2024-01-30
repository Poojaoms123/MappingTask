package com.example.College.Service;

import com.example.College.Model.SaveRequest.SaveStudentRequest;
import com.example.College.Model.Student;

import java.util.List;

public interface StudentService {
    Object saveOrUpdateStudent(SaveStudentRequest saveCollegeRequest, Long collegeId) throws Exception;

    Object getStudentById(Long studentId) throws Exception;

    List<Student> getAllStudent();

    Object deletedStudentById(Long studentId) throws Exception;

    Object changeStudentStatus(Long studentId) throws Exception;
}
