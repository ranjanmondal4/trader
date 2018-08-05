package com.trader.controller.redis;

import com.trader.service.redis.RedisService;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    private RedisService redisService;

    @RequestMapping(value="/trader/api/v1/marketOrder", method= RequestMethod.POST)
    ResponseEntity<Object> addMarketOrder(@RequestParam String market, @RequestParam double amount){
        LOGGER.info(">>>>>>>>>>>>>>> addMarketOrder");
        Response response = redisService.addMarketOrder(market, amount);
        if(response.isSuccess())
            return ResponseHandler.generateControllerResponse(response, HttpStatus.OK);
        else
            return ResponseHandler.generateControllerResponse(response, HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value="/trader/api/v1/marketOrder", method= RequestMethod.GET)
    ResponseEntity<Object> getMarketOrder(@RequestParam String market, @RequestParam double amount){
        LOGGER.info(">>>>>>>>>>>>>>> get MarketOrder");
        Response response = redisService.getMarketOrder(market, amount);
        if(response.isSuccess())
            return ResponseHandler.generateControllerResponse(response, HttpStatus.OK);
        else
            return ResponseHandler.generateControllerResponse(response, HttpStatus.BAD_REQUEST);

    }
/*
    @RequestMapping(value="/trader/api/v1/marketOrder", method= RequestMethod.PUT)
    ResponseEntity<Object> updateMarketOrder(@RequestParam long marketId, @RequestParam double amount){
        LOGGER.info(">>>>>>>>>>>>>>> updateMarketOrder");
        Response response = redisService.updateMarketOrder(marketId, amount);
        if(response.isSuccess())
            return ResponseHandler.generateControllerResponse(response, HttpStatus.OK);
        else
            return ResponseHandler.generateControllerResponse(response, HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value="/trader/api/v1/marketOrder", method= RequestMethod.DELETE)
    ResponseEntity<Object> deleteMarketOrder(@RequestParam long marketId){
        LOGGER.info(">>>>>>>>>>>>>>> delete MarketOrder");
        Response response = redisService.deleteMarketOrder(marketId);
        if(response.isSuccess())
            return ResponseHandler.generateControllerResponse(response, HttpStatus.OK);
        else
            return ResponseHandler.generateControllerResponse(response, HttpStatus.BAD_REQUEST);

    }*/
}
