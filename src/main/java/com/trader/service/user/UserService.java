package com.trader.service.user;

import java.util.HashMap;
import java.util.Map;

import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trader.dto.user.LoginReceiveDTO;

@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public Response login(LoginReceiveDTO loginDTO){
		LOGGER.debug("service is invoked");
		return ResponseHandler.generateServiceResponse(true, "Success", loginDTO, null);
	}
	
	public Response getByUserId(long userId){
		LOGGER.debug(":::::::::::::::::::: get by userId service is invoked");
		Map<String, Object> user = new HashMap<>();
		user.put("name", "rnajan");
		user.put("age", 20);
		return ResponseHandler.generateServiceResponse(true, "Success", user, null);
	}
}
