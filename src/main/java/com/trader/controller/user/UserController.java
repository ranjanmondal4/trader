package com.trader.controller.user;

import com.trader.config.BasicConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.dto.common.ServiceDTO;
import com.trader.dto.user.LoginReceiveDTO;
import com.trader.service.user.UserService;
import com.trader.utils.ResponseHandler;

@RestController
@Api(description = "Set of endpoints for CRUD Operation for Users")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private BasicConfig basicConfig;
	@ApiOperation("User Login")
	@RequestMapping(value="/trader/api/v1/user/login", method=RequestMethod.POST)
	ResponseEntity<Object> login(@RequestBody LoginReceiveDTO loginDTO){
		LOGGER.info("login is called " + basicConfig.getEvnName());

		ServiceDTO serviceDto = userService.login(loginDTO);
		return ResponseHandler.generateResponse(serviceDto, HttpStatus.OK, HttpStatus.BAD_REQUEST); 
	}

	/*@ApiOperation(value = "/trader/api/v1/user/getByUserId",
			authorizations = {
					@Authorization(value="Authorization", scopes = {@AuthorizationScope(
							scope = "add:pet",
							description = "allows adding of pets")
					})
			}
	)*/
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@RequestMapping(value="/trader/api/v1/user/getByUserId", method=RequestMethod.GET)//@RequestParam String Authorization,
	ResponseEntity<Object> getByUserId(@RequestParam long userId){
		LOGGER.debug("getUserByUserId is called");
		ServiceDTO serviceDto = userService.getByUserId(userId);
		return ResponseHandler.generateResponse(serviceDto, HttpStatus.OK, HttpStatus.BAD_REQUEST); 
	}
}
