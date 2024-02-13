package com.example.College.Service.ServiceImpl;

import com.example.College.Model.College;
import com.example.College.Model.Response.pageDTO;
import com.example.College.Model.SaveRequest.SaveCollegeRequest;
import com.example.College.Model.Student;
import com.example.College.Repository.CollegeRepository;
import com.example.College.Repository.Projection.StudentProjection;
import com.example.College.Service.CollegeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    CollegeRepository collegeRepository;

    @Override
    public Object saveOrUpdateCollege(SaveCollegeRequest saveCompanyRequest) {
        if (collegeRepository.existsById(saveCompanyRequest.getCollegeId())) {
            College college = collegeRepository.findById(saveCompanyRequest.getCollegeId()).get();
            college.setCollegeName(saveCompanyRequest.getCollegeName());
            college.setCollegeWebsite(saveCompanyRequest.getCollegeWebsite());
            college.setCollegeFeild(saveCompanyRequest.getCollegeFeild());
            college.setProfileImage(saveCompanyRequest.getProfileImage());
            college.setCollegeIsDeleted(false);
            college.setCollegeIsActive(true);
            collegeRepository.save(college);
            return "updated sucessfully ";
        } else {
            College college = new College();
            college.setCollegeName(saveCompanyRequest.getCollegeName());
            college.setCollegeWebsite(saveCompanyRequest.getCollegeWebsite());
            college.setCollegeFeild(saveCompanyRequest.getCollegeFeild());
            college.setProfileImage(saveCompanyRequest.getProfileImage());
            college.setCollegeIsDeleted(false);
            college.setCollegeIsActive(true);
            collegeRepository.save(college);
            return "saved sucessfully";

        }
    }

    @Override
    public Object getCollegeById(Long collegeId) throws Exception {
        if (collegeRepository.existsById(collegeId)) {
            College college = collegeRepository.findById(collegeId).get();
            return college;
        } else {
            throw new Exception("college not found");
        }

    }

    @Override
    public Object deleteById(Long collegeId) throws Exception {
        if (collegeRepository.existsById((collegeId))) {
            College college = collegeRepository.findById(collegeId).get();
            college.setCollegeIsDeleted(false);
            collegeRepository.save(college);
            return "deleted successfully";
        } else {
            throw new Exception("college not found");
        }
    }

    @Override
    public Object changeCollegeStatus(Long collegeId) throws Exception {
        if (collegeRepository.existsById(collegeId)) {
            College college = collegeRepository.findById(collegeId).get();
            if (college.getCollegeIsDeleted()) {
                college.setCollegeIsDeleted(false);
                collegeRepository.save(college);
                return "inactive";
            } else {
                college.setCollegeIsDeleted(false);
                return "college active";
            }

        } else {
            throw new Exception("college not found");
        }
    }

    @Override
    public List<College> getAllCollege() {
        List<College> college = collegeRepository.getAllCollege();
        return college;
    }

    @Override
    public Object getStudentByCollegeId(Long collegeId, String studentName, String studentEmail, String studentMobileNo, String studentCode, Pageable pageable) {
        Page<Student> college = null;
        if (StringUtils.isNotBlank(studentName) || StringUtils.isNotBlank(studentEmail) || StringUtils.isNotBlank(studentMobileNo) || StringUtils.isNotBlank(studentCode) || collegeId != null) {
            if (StringUtils.isNotBlank(studentName)) {
                studentName = studentName.toLowerCase();
            } else {
                studentName = null;
            }
            if (StringUtils.isNotBlank(studentEmail)) {
                studentEmail = studentEmail.toLowerCase();
            } else {
                studentEmail = null;
            }
            if (StringUtils.isNotBlank(studentCode)) {
                studentCode = studentCode.toLowerCase();
            } else {
                studentCode = null;
            }
            if (StringUtils.isNotBlank(studentMobileNo)) {
                studentMobileNo = null;
            }
            college = collegeRepository.getStudentByCollegeId(collegeId, studentName, studentEmail, studentMobileNo, studentCode, pageable);
        } else {
            college = collegeRepository.getByCollegeId(collegeId, pageable);
        }
        return new pageDTO(college.getContent(), college.getTotalElements(), college.getNumber(), college.getTotalPages());
    }



    @Override
    public Object getAllCollegeStudent(String collegeName, String studentName, String studentEmail, String studentMobileNo, String studentCode, String collegeWebsite,Pageable pageable) {
        Page<StudentProjection> college = null;
        if (StringUtils.isNotBlank(studentName) || StringUtils.isNotBlank(studentEmail) || StringUtils.isNotBlank(studentMobileNo) || StringUtils.isNotBlank(studentCode) || StringUtils.isNotBlank(collegeName) || StringUtils.isNotBlank(collegeWebsite)) {
            if (StringUtils.isNotBlank(studentName)) {
                studentName = studentName.toLowerCase();
            } else {
                studentName = null;
            }
            if (StringUtils.isNotBlank(studentEmail)) {
                studentEmail = studentEmail.toLowerCase();
            } else {
                studentEmail = null;
            }
            if (StringUtils.isNotBlank(studentCode)) {
                studentCode = studentCode.toLowerCase();
            } else {
                studentCode = null;
            }
            if (StringUtils.isNotBlank(collegeName)) {
                collegeName = collegeName.toLowerCase();
            } else {
                collegeName = null;
            }
            if (StringUtils.isNotBlank(collegeWebsite)) {
                collegeWebsite = collegeWebsite.toLowerCase();
            } else {
                collegeWebsite = null;
            }
            if (StringUtils.isNotBlank(studentMobileNo)) {
                studentMobileNo = null;
            }
            college = collegeRepository.getAllCollegeStudent(collegeName, studentName, studentEmail, studentMobileNo, studentCode, collegeWebsite, pageable);
        } else {
            college = collegeRepository.getAllCollegeStudent(pageable);
        }
        return new pageDTO(college.getContent(), college.getTotalElements(), college.getNumber(), college.getTotalPages());
    }



}
