package com.trader.controller.async;

import com.trader.service.async.AsyncService;
import com.trader.service.user.UserService;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class AsyncController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private UserService userService;
    @RequestMapping(value="/trader/api/v1/async", method= RequestMethod.GET)
    ResponseEntity<Object> callAsync(){
        LOGGER.info(">>>>>>>>>>>>>Async is called ");
        userService.getUserByAsync();
        return ResponseHandler.generateControllerResponse(null, HttpStatus.OK);
    }
}
