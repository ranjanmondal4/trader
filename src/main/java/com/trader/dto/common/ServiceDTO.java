package com.trader.dto.common;


public class ServiceDTO {
	private boolean success;
	private Object data;
	private String message;
	private int statusCode;
	private long timeStamp;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	private ServiceDTO(){}
	
	public static ServiceDTO getServiceResponse(boolean isSuccess, Object data, String message){
		ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.success = isSuccess;
		serviceDTO.data = data;
		serviceDTO.message = message;
		return serviceDTO;
	}
	
	public static ServiceDTO getControllerResponse(ServiceDTO serviceDTO, int statusCode, long timeStamp){
		serviceDTO.statusCode = statusCode;
		serviceDTO.timeStamp = timeStamp;
		return serviceDTO;
	}
	
}
