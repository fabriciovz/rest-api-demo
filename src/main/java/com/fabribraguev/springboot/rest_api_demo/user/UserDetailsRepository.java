package com.fabribraguev.springboot.rest_api_demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
        List<UserDetails> findByRole(String role);
}
