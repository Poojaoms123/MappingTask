package com.example.College.Service.ServiceImpl;

import com.example.College.Model.College;
import com.example.College.Model.SaveRequest.SaveCollegeRequest;
import com.example.College.Repository.CollegeRepository;
import com.example.College.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    CollegeRepository collegeRepository;

    @Override
    public Object saveOrUpdateCollege(SaveCollegeRequest saveCompanyRequest) {
        if (collegeRepository.existsById(saveCompanyRequest.getCollegeId())){
            College college = collegeRepository.findById(saveCompanyRequest.getCollegeId()).get();
            college.setCollegeName(saveCompanyRequest.getCollegeName());
            college.setCollegeWebsite(saveCompanyRequest.getCollegeWebsite());
            college.setCollegeFeild(saveCompanyRequest.getCollegeFeild());
            college.setProfileImage(saveCompanyRequest.getProfileImage());
            college.setCollegeIsDeleted(false);
            college.setCollegeIsActive(true);
            collegeRepository.save(college);
            return "updated sucessfully ";
        }else {
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
        }else {
            throw new Exception("college not found");
        }

    }

    @Override
    public Object deleteById(Long collegeId) throws Exception {
        if (collegeRepository.existsById((collegeId))){
            College college = collegeRepository.findById(collegeId).get();
            college.setCollegeIsDeleted(false);
            collegeRepository.save(college);
            return "deleted successfully";
        }else {
            throw new Exception("college not found");
        }
    }

    @Override
    public Object changeCollegeStatus(Long collegeId) throws Exception {
        if (collegeRepository.existsById(collegeId)){
            College college = collegeRepository.findById(collegeId).get();
            if (college.getCollegeIsDeleted()){
                college.setCollegeIsDeleted(false);
                collegeRepository.save(college);
                return "inactive";
            }else {
                college.setCollegeIsDeleted(false);
                return "college active";
            }

        }else {
            throw new  Exception("college not found");
        }
    }

    @Override
    public List<College> getAllCollege() {
        List<College> college= collegeRepository.getAllCollege();
        return  college;
    }


}
