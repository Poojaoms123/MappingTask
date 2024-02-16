package com.example.College.Controller;

import com.example.College.Model.Response.EntityResponse;
import com.example.College.Model.User;
import com.example.College.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    @GetMapping("/firstapi11")
    public String firstapi11() {
        return "working";
    }
    @PostMapping("/importExcel")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file){
        try {
            return new ResponseEntity<>(new EntityResponse(userService.importExcel(file),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1), HttpStatus.OK);
        }
    }

    @GetMapping("/exportExcel")
    public ResponseEntity<?> exportExcel(@RequestParam(value = "extension",required = false) String extension ) throws Exception {
        String fileName="";
        if (extension.equalsIgnoreCase("xlsx")){
            fileName="UserDB.xlsx";
        }else if (extension.equalsIgnoreCase("csv")){
            fileName="UserDB.csv";
        }else if (extension.equalsIgnoreCase("xls")) {
            fileName="UserDB.xls";
        }else {
            fileName="UserDB.xlsx";

        }
                InputStreamResource file = new InputStreamResource(userService.exportExcel());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .contentType(MediaType.parseMediaType("application/csv")).body(file);


    }

}


