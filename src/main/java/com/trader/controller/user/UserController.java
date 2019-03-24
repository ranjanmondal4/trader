package com.trader.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trader.constant.UrlMapping;
import com.trader.dto.user.LoginReceiveDTO;
import com.trader.service.user.UserService;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService; 
	@RequestMapping(value="/trader/api/v1/user/login", method=RequestMethod.POST)
    public Response<Object> login(@RequestBody LoginReceiveDTO loginDTO){
        LOGGER.info("User login is invoked");
        return userService.login(loginDTO);
        //return ResponseHandler.generateServiceResponse(true, "Success", "Success", null);
    }

}
