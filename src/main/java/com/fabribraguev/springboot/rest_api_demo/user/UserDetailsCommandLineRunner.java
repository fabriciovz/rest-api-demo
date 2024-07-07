package com.fabribraguev.springboot.rest_api_demo.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsRepository userDetailsRepository;

    public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(Arrays.toString(args));

        userDetailsRepository.save(new UserDetails("Luis  ", "Root"));
        userDetailsRepository.save(new UserDetails("Fabricio", "Admin"));
        userDetailsRepository.save(new UserDetails("Rodrigo", "Admin"));
        userDetailsRepository.save(new UserDetails("Fabian  ", "Admin"));

        //List<UserDetails> users = userDetailsRepository.findAll();
        List<UserDetails> users = userDetailsRepository.findByRole("Root");

        users.forEach(user -> logger.info(user.toString()));
    }


}
