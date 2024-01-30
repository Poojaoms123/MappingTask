package com.example.College.Model.SaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCollegeRequest {
    private Long collegeId;
    private String collegeName;
    private String collegeWebsite;
    private String collegeFeild;
    private String profileImage;
}
