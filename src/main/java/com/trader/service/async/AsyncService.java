package com.trader.service.async;

import com.trader.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public Future<User> createAndReturnUser() {
        System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
        try {
            User user = new User();
            user.setName("Jon Doe");

            Thread.sleep(5000);
            //return new AsyncResult(user);
        } catch (InterruptedException e) {
            LOGGER.info("Exception in create User {}", e);
        }
        return null;
    }

    @Async("threadPoolExecutor")
    public void createUserWithThreadPoolExecutor(){
        System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
        System.out.println("User created with thread pool executor");
    }

    /*@Async("concurrentTaskExecutor")
    public void createUserWithConcurrentExecutor(){
        System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
        System.out.println("User created with concurrent task executor");
    }*/
}
