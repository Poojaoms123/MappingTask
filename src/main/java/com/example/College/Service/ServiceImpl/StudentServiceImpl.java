package com.example.College.Service.ServiceImpl;

import com.example.College.Model.College;
import com.example.College.Model.SaveRequest.SaveStudentRequest;
import com.example.College.Model.Student;
import com.example.College.Repository.CollegeRepository;
import com.example.College.Repository.StudentRepository;
import com.example.College.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private CollegeRepository collegeRepository;

    @Override
    public Object saveOrUpdateStudent(SaveStudentRequest saveStudentRequest, Long collegeId) throws Exception {
        if (studentRepository.existsById(saveStudentRequest.getStudentId())){
            Student student = studentRepository.findById(saveStudentRequest.getStudentId()).get();
            student.setStudentName(saveStudentRequest.getStudentName());
            student.setStudentEmail(saveStudentRequest.getStudentEmail());
            student.setStudentMobileNo(saveStudentRequest.getStudentMobileNo());
            student.setStudentCode(saveStudentRequest.getStudentCode());
            student.setProfileImage(saveStudentRequest.getProfileImage());
            student.setStudentIsDeleted(false);
            student.setStudentIsActve(true);
            College college;
            if (collegeRepository.existsByCollegeIdAndCollegeIsDeleted(collegeId,false)){
                 college = collegeRepository.findByCollegeIdAndCollegeIsDeleted(collegeId,false);
                student.setCollege(college);
            }else {
                throw new Exception("college not found");
            }
            String studentCode = this.generateStudentCode(college.getCollegeName());
            student.setStudentCode(studentCode);
            studentRepository.save(student);
            return "updated sucessfully";

        }else {
            Student student = new Student();
            student.setStudentName(saveStudentRequest.getStudentName());
            student.setStudentEmail(saveStudentRequest.getStudentEmail());
            student.setStudentMobileNo(saveStudentRequest.getStudentMobileNo());
            student.setStudentCode(saveStudentRequest.getStudentCode());
            student.setProfileImage(saveStudentRequest.getProfileImage());
            student.setStudentIsDeleted(false);
            student.setStudentIsActve(true);
            College college;
            if (collegeRepository.existsByCollegeIdAndCollegeIsDeleted(collegeId,false)){
                college = collegeRepository.findByCollegeIdAndCollegeIsDeleted(collegeId,false);
                student.setCollege(college);
            }else {
                throw new Exception("college not found");
            }
            String studentCode = this.generateStudentCode(college.getCollegeName());
            student.setStudentCode(studentCode);
            studentRepository.save(student);
            return "saved sucessfully";

        }
    }
    public String generateStudentCode(String collegeName){
        String studentCode = collegeName.replaceAll("","").substring(0,3)+"00";
        return studentCode;
    }

    @Override
    public Object getStudentById(Long studentId) throws Exception {
        if (studentRepository.existsById(studentId)) {
            Student student = studentRepository.findById(studentId).get();
            return student;
        }else {
            throw new Exception("student not found");
        }
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> student= studentRepository.getAllStudent();
        return  student;
    }

    @Override
    public Object deletedStudentById(Long studentId) throws Exception {
        if (studentRepository.existsById((studentId))){
            Student student = studentRepository.findById(studentId).get();
            student.setStudentIsDeleted(false);
            studentRepository.save(student);
            return "deleted successfully";
        }else {
            throw new Exception("student not found");
        }
    }

    @Override
    public Object changeStudentStatus(Long studentId) throws Exception {
        if (studentRepository.existsById(studentId)){
            Student student=studentRepository.findById(studentId).get();
            if( student.getStudentIsDeleted()){
                student.setStudentIsDeleted(false);
                studentRepository.save(student);
                return "inactive";
            }else {
                student.setStudentIsDeleted(false);
                return "student active";
            }

        }else {
            throw new  Exception("college not found");
        }
    }
}
