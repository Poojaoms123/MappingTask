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
@Table(name = "College_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_mobile_no")
    private String userMobileNo;

    @Column(name = "user_is_deleted")
    private Boolean userIsDeleted;

    @Column(name = "user_is_active")
    private Boolean userIsActive;

    @CreationTimestamp
    @Column(name = "user_created_at",updatable = false)
    private LocalDateTime userCreatedAt;

    @UpdateTimestamp
    @Column(name = "user_updated_at")
    private  LocalDateTime userUpdatedAt;
}
