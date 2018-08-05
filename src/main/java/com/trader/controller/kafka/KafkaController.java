package com.trader.controller.kafka;

import com.trader.service.kafka.KafkaService;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaService kafkaService;

    @RequestMapping(value="/trader/api/v1/user/login", method= RequestMethod.GET)
    ResponseEntity<Object> login(){
        LOGGER.info(">>>>>>>>>>>>>login is called ");
        Response response = kafkaService.login();
        if(response.isSuccess())
            return ResponseHandler.generateControllerResponse(null, HttpStatus.OK);
        return ResponseHandler.generateControllerResponse(null, HttpStatus.BAD_REQUEST);
    }
}
