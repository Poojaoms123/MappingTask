package com.example.College.Model.SaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveStudentRequest {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String studentMobileNo;
    private String studentCode;
    private String profileImage;
}
