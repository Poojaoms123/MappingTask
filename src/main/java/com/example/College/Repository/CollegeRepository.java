package com.example.College.Repository;

import com.example.College.Model.College;
import com.example.College.Model.Student;
import com.example.College.Repository.Projection.StudentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollegeRepository extends JpaRepository<College,Long> {
    @Query(value = "select * from College_College where college_is_deleted=false ",nativeQuery = true)
    List<College> getAllCollege();

    College findByCollegeIdAndCollegeIsDeleted(Long collegeId, boolean b);

    boolean existsByCollegeIdAndCollegeIsDeleted(Long collegeId, boolean b);

    @Query(value = "select s from Student  as s inner join College  as c on s.college.collegeId = c.collegeId where s.college.collegeId = :collegeId and (:studentName is null or upper(s.studentName) like %:studentName%) and (:studentEmail is null or upper(s.studentEmail) like %:studentEmail%) and (:studentMobileNo is null or upper(s.studentMobileNo) like %:studentMobileNo%) and (:studentCode is null or upper(s.studentCode) like %:studentCode%)and s.studentIsDeleted=false ")
    Page<Student> getStudentByCollegeId(Long collegeId, String studentName, String studentEmail, String studentMobileNo, String studentCode, Pageable pageable);

    @Query(value = "select s from Student as s inner join College  as c on s.college.collegeId = c.collegeId where s.college.collegeId = c.collegeId")
    Page<Student> getByCollegeId(Long collegeId, Pageable pageable);

    @Query(value = "select s.studentId as studentId, s.studentName as studentName,s.studentEmail as studentEmail,s.studentMobileNo as studentMobileNo,s.studentCode as studentCode,s.studentCreatedAt as createdAt,s.studentIsActive as studentIsActive,s.college.collegeId as collegeId,s.college.collegeName as collegeName, s.college.collegeWebsite as collegeWebsite from Student as s where (:collegeName is null or upper(s.college.collegeName) like %:collegeName%) and (:studentName is null or upper(s.studentName) like %:studentName%) and (:studentEmail is null or upper(s.studentEmail) like %:studentEmail%) and (:studentMobileNo is null or upper(s.studentMobileNo) like %:studentMobileNo%) and (:studentCode is null or upper(s.studentCode) like %:studentCode%) and (:collegeWebsite is null or upper(s.college.collegeWebsite) like %:collegeWebsite%) and s.studentIsDeleted=false and s.college.collegeIsDeleted=false order by s.studentCreatedAt desc")
    Page<StudentProjection> getAllCollegeStudent(String collegeName, String studentName, String studentEmail, String studentMobileNo, String studentCode, String collegeWebsite, Pageable pageable);

    @Query(value = "select s.studentId as studentId, s.studentName as studentName,s.studentEmail as studentEmail,s.studentMobileNo as studentMobileNo,s.studentCode as studentCode,s.studentCreatedAt as createdAt,s.studentIsActive as studentIsActive,s.college.collegeId as collegeId,s.college.collegeName as collegeName, s.college.collegeWebsite as collegeWebsite from Student as s where  s.college.collegeIsDeleted=false and s.studentIsDeleted=false order by s.studentCreatedAt desc ",nativeQuery = false)
    Page<StudentProjection> getAllCollegeStudent(Pageable pageable);

}
