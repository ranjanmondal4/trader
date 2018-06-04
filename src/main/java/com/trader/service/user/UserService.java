package com.trader.service.user;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trader.dto.common.ServiceDTO;
import com.trader.dto.user.LoginReceiveDTO;

@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public ServiceDTO login(LoginReceiveDTO loginDTO){
		LOGGER.debug("service is invoked");
		return ServiceDTO.getServiceResponse(true, loginDTO, "Success");
	}
	
	public ServiceDTO getByUserId(long userId){
		LOGGER.debug(":::::::::::::::::::: get by userId service is invoked");
		Map<String, Object> user = new HashMap<>();
		user.put("name", "rnajan");
		user.put("age", 20);
		return ServiceDTO.getServiceResponse(true, user, "Success");
	}
}
