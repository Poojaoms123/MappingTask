package com.example.College.Controller;

import com.example.College.Model.College;
import com.example.College.Model.Response.EntityResponse;
import com.example.College.Model.SaveRequest.SaveCollegeRequest;
import com.example.College.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/college")
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @Autowired
    @GetMapping("/firstapi")
    public String firstapi() {
        return "Working";
    }

    @PostMapping("/saveOrUpdateCollege")
    public ResponseEntity<?> saveOrUpdateCollege(@RequestBody SaveCollegeRequest saveCompanyRequest) throws Exception {
        try {
            return new ResponseEntity<>(new EntityResponse(collegeService.saveOrUpdateCollege(saveCompanyRequest), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/getCollegeById")
    public ResponseEntity<?> getCollegeById(@RequestParam Long collegeId) {
        try {
            return new ResponseEntity<>(new EntityResponse(collegeService.getCollegeById(collegeId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteCollegeById")
    public ResponseEntity<?> deleteCollegeById(@RequestParam Long collegeId) {
        try {
            return new ResponseEntity<>(new EntityResponse(collegeService.deleteById(collegeId), 0), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1),HttpStatus.OK);
        }
    }

    @PutMapping("/changeCollegeStatus")
    public ResponseEntity<?> changeCollegeStatus(@RequestParam Long collegeId) {
        try {
            return new ResponseEntity<>(new EntityResponse(collegeService.changeCollegeStatus(collegeId), 0),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getAllCollege", method = RequestMethod.GET)
    private ResponseEntity<?> getAllCollege() {
        try {
            return new ResponseEntity<>(new EntityResponse(collegeService.getAllCollege(), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }


    }
}
