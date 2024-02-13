package com.example.College.Repository.Projection;

import java.time.LocalDateTime;

public interface StudentProjection {
    Long getStudentId();
    String getStudentName();
    String getStudentEmail();
    String getStudentMobileNo();
    String getStudentCode();
    LocalDateTime getCreatedAt();
    String getStudentIsActive();
    String getCollegeId();
    String getCollegeName();
    String getcollegeWebsite();

}
