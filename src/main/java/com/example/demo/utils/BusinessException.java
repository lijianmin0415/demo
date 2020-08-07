package com.example.demo.utils;

/**
 * 自定义业务异常基类
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public BusinessException() {
		super();
	}

	public BusinessException(final String message) {
		this.message = message;
	}

	public BusinessException(final Throwable cause) {
		super(cause);
	}

	public String getMessage() {
		return message;
	}
}
