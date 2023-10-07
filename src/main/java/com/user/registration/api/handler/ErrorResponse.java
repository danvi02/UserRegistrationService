package com.user.registration.api.handler;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
	
	private String exceptionDetails;
	private String developerMessage;// So that the end user can easily understand what type of error this is
	private String timestamp;
}
