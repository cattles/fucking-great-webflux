package com.example.webflux.service;

import com.example.webflux.domain.User;
import com.example.webflux.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author cattle -  稻草鸟人
 * @date 2020/5/10 下午12:52
 */
@Service
public class UserService {

    @Resource
    UserRepository userRepository;

    public Mono<User> find(Long id) {
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {

        }
        return userRepository.find(id);
    }

    public Mono<User> create(User user) {
        return userRepository.create(user);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
