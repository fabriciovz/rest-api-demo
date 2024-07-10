package com.fabribraguev.springboot.rest_api_demo;


import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
@ConfigurationProperties("api-auth")
@Data
public class ApiAuthConfig {
    private String user;
    private String password;
}
