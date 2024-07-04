package com.example.filter.controller;

import com.example.filter.interceptor.OpenApi;
import com.example.filter.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@OpenApi
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @OpenApi
    @PostMapping("")
    public UserRequest register(
            @RequestBody
            UserRequest userRequest
            //HttpEntity http
    ){
        throw new NumberFormatException("");

        //log.info("{}", userRequest);
        //return userRequest; //서버에서 클라이언트로 어떤 값을 내렸는지 정확히 알 수 없음
    }

    @GetMapping("/hello")
    public void hello(){
        log.info("hello");
    }
}
