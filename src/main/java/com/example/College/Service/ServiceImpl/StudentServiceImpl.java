package com.example.College.Service.ServiceImpl;

import com.amazonaws.util.IOUtils;
import com.example.College.Model.College;
import com.example.College.Model.SaveRequest.SaveStudentRequest;
import com.example.College.Model.Student;
import com.example.College.Repository.CollegeRepository;
import com.example.College.Repository.StudentRepository;
import com.example.College.Service.StudentService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.aspectj.apache.bcel.classfile.Deprecated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

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
            student.setStudentIsActive(true);
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
            student.setStudentIsActive(true);
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

            int otp = OtpService.generateOtp(saveStudentRequest.getStudentEmail());
            int otp1 = OtpService.getOtp(saveStudentRequest.getStudentEmail());
            OtpService.clearOtp(saveStudentRequest.getStudentEmail());


            Context context = new Context();
            context.setVariable("name",student.getStudentName());
            String process = templateEngine.process("EmailTemplate.html", context);

            File file = File.createTempFile("template",".pdf");
            InputStream  inputStream = new FileInputStream(file);
            FileInputStream fileOutputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("converted",file.getName(),".pdf", IOUtils.toByteArray(fileOutputStream));
            HtmlConverter.convertToPdf(process,new FileOutputStream(file));
            this.sendEmailWithAttachment("suryawanshipooja.sp@gmail.com","Application","Hello",multipartFile);

            this.sendEmailToHtmlTemplate("suryawanshipooja.sp@gmail.com","Application",process );

            this.sendEmail("suryawanshipooja.sp@gmail.com","Application","Hi Pooja" + otp);
            return "saved sucessfully";

        }
    }
    public String generateStudentCode(String collegeName){
        String studentCode = collegeName.replaceAll("","").substring(0,3)+"00";
        return studentCode;
    }

    public  void sendEmail(String to,String subject,String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
    }

    public void sendEmailToHtmlTemplate(String to,String subject,String body){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");
        try{
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String body, MultipartFile attachment) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            // Add Attachment
            helper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
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
