package com.trader.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trader.dto.common.ServiceDTO;

public class ResponseHandler {
	
	public static ResponseEntity<Object> generateResponse(ServiceDTO serviceDto, HttpStatus successStatus,
			HttpStatus failureStatus){
		Date now = new Date();
		try {
			if(serviceDto.isSuccess()){
				serviceDto = ServiceDTO.getControllerResponse(serviceDto, successStatus.ordinal(), now.getTime());
				return new ResponseEntity<Object>(serviceDto, successStatus);
			}else {
				serviceDto = ServiceDTO.getControllerResponse(serviceDto, failureStatus.ordinal(), now.getTime());
				return new ResponseEntity<Object>(serviceDto, failureStatus);
			}
		}catch(Exception e){
			serviceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.ordinal());
			return new ResponseEntity<Object>(serviceDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
