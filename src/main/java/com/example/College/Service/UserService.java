package com.example.College.Service;

import com.example.College.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface UserService {
    String importExcel(MultipartFile file) throws Exception;


    InputStream exportExcel() throws Exception;
}
