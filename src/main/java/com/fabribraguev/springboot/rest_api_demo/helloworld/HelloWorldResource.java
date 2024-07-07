package com.fabribraguev.springboot.rest_api_demo.helloworld;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class HelloWorldResource {
    @RequestMapping("/hello-world")
    public String helloWorld(){
        return "HelloWorld, I'm here";
    }
    @RequestMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("HelloWorld, I'm here");
    }

    //Path variable or path params
    // user/fabribraguev/todos/1
    @RequestMapping("/hello-world-path-param/{name}")
    public HelloWorldBean helloWorldPathParam(@PathVariable String name){
        return new HelloWorldBean("HelloWorld " + name);
    }

    @RequestMapping("/hello-world-path-param/{name}/message/{message}")
    public HelloWorldBean helloWorldMultiplePathParam(@PathVariable String name,
                                              @PathVariable String message){
        return new HelloWorldBean("HelloWorld " + name + "," + message);
    }
}
