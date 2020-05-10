package com.example.webflux.repository;

import com.example.webflux.domain.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cattle -  稻草鸟人
 * @date 2020/5/10 下午1:07
 */
@Repository
public class UserRepository {

    private Map<Long, User> userMap = new HashMap<>();

    @PostConstruct
    public void init() {
        userMap.put(1L, new User(1L, "Cattle", "Ma"));
        userMap.put(2L, new User(2L, "Tony", "Ma"));
        userMap.put(3L, new User(3L, "Jack", "Ma"));
    }

    public Mono<User> find(Long id) {
        return Mono.just(userMap.get(id));
    }

    public Mono<User> create(User user) {
        userMap.put(user.getId(), user);
        return Mono.just(userMap.get(user.getId()));
    }


    public Flux<User> findAll() {
        Flux<User> userFlux = Flux.fromIterable(userMap.entrySet().stream().map(Map.Entry::getValue)
                .collect(Collectors.toList()));

        return userFlux;
    }

}
