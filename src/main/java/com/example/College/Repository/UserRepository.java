package com.example.College.Repository;

import com.example.College.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByUserEmail(String userEmail);

    boolean existsByUserMobileNo(String userMobileNo);

}
