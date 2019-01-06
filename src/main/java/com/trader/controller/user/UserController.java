package com.trader.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trader.constant.UrlMapping;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;

public class UserController {
	 private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	 
	@RequestMapping(value=UrlMapping.USER_LOGIN, method=RequestMethod.PUT)
    public Response<Object> login(){
        LOGGER.debug("User login is invoked");
        return ResponseHandler.generateServiceResponse(true, "Success", "Success", null);
    }

}
