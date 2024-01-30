package com.example.College.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "College_College")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "collge_id")
    private Long collegeId;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "college_Website")
    private String collegeWebsite;

    @Column(name = "college_feild")
    private String collegeFeild;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "college_is_deleted")
    private Boolean collegeIsDeleted;

    @Column(name = "college_is_acitve")
    private Boolean collegeIsActive;

    @CreationTimestamp
    @Column(name = "college_created_at",updatable = false)
    private LocalDateTime collegeCreatedAt;

    @UpdateTimestamp
    @Column(name = "college_updated_at")
    private LocalDateTime collegeUpdatedAt;


}
