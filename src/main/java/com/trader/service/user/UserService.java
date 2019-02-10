package com.trader.service.user;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import com.trader.domain.user.User;
import com.trader.service.async.AsyncService;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trader.dto.user.LoginReceiveDTO;

@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AsyncService asyncService;

	public Response<Object> login(LoginReceiveDTO loginDTO){
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

	public Response getUserByAsync(){
		asyncService.createUserWithThreadPoolExecutor();
		//asyncService.createUserWithConcurrentExecutor();
		Future future = asyncService.createAndReturnUser();
		try {
			User user = (User) future.get();
			LOGGER.info("Future : {}", user.getName());
		}catch (Exception e){
			LOGGER.error("Exception in Future {}", e);
		}
		return ResponseHandler.generateServiceResponse(true, "Success", null, null);
	}
}
