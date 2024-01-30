package com.example.College.Controller;

import com.example.College.Model.Response.EntityResponse;
import com.example.College.Model.SaveRequest.SaveCollegeRequest;
import com.example.College.Model.SaveRequest.SaveStudentRequest;
import com.example.College.Model.Student;
import com.example.College.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    @GetMapping("/firstapi")
    public String firstapi(){
        return "its Working";
    }
    @PostMapping("/saveOrUpdateStudent")
    public ResponseEntity<?>saveOrUpdateStudent(@RequestBody SaveStudentRequest saveStudentRequest,
                                                @RequestParam Long collegeId) throws Exception{
        try {
            return new ResponseEntity<>(new EntityResponse(studentService.saveOrUpdateStudent(saveStudentRequest,collegeId), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }

    }
    @GetMapping("/getStudentById")
    public ResponseEntity<?>getStudentId(@RequestParam Long studentId) throws Exception{
        try {
                return new ResponseEntity<>(new EntityResponse(studentService.getStudentById(studentId),0),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getAllStudent",method = RequestMethod.GET)
    private List<Student>getAllStudent(){
        return studentService.getAllStudent();
    }

    @DeleteMapping("/deletedStudentById")
    public ResponseEntity<?>deletedStudentById(@RequestParam Long studentId) {
        try {
            return new ResponseEntity<>(new EntityResponse(studentService.deletedStudentById(studentId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PutMapping("/changeStudentStatus")
    public ResponseEntity<?>changeStudentStatus(@RequestParam Long studentId){
        try {
            return new ResponseEntity<>(new EntityResponse(studentService.changeStudentStatus(studentId),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
}
