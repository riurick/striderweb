package com.strider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SampleEntityNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public SampleEntityNotFoundException(String error) {
		super(error);
	}
}
