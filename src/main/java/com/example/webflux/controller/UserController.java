package com.example.webflux.controller;

import com.example.webflux.domain.User;
import com.example.webflux.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author cattle -  稻草鸟人
 * @date 2020/5/10 下午1:14
 */
@RestController
@RequestMapping("/v1")
public class UserController {


    @Resource
    UserService userService;

    @GetMapping(path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<User> getUser(@PathVariable("userId") Long userId) {
        return userService.find(userId).log();
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findUser() {
        return userService.findAll().delayElements(Duration.ofSeconds(1L)).log();
    }
}
