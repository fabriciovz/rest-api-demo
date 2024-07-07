package com.fabribraguev.springboot.rest_api_demo.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "userDetails" ,path = "userDetails")
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails,Long> {
        List<UserDetails> findByRole(String role);
}
