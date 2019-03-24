package com.trader.utils;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

	private ResponseHandler(){}

	public static Response<Object> generateServiceResponse(boolean isSuccess, String message,
												   Object data, Exception error){
		Response<Object> response = null;
		try {
			response = new Response<>(isSuccess, message, data, error);
		}catch(Exception e){
			response = new Response<>(false, e.getMessage(), null, e);
		}
		return response;
	}

	public static ResponseEntity<Object> generateControllerResponse(Response<Object> response, HttpStatus status) {
		if(Objects.isNull(response)){
			response = new Response<Object>(false, "message", null, null);
		}
		try {
			response.setStatus(status.value());
			return new ResponseEntity<Object>(response, status);
		} catch (Exception e) {
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
