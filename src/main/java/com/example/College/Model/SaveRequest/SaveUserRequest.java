package com.example.College.Model.SaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserRequest {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userMobileNo;
}
