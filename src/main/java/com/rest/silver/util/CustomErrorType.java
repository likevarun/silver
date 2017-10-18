package com.rest.silver.util;

/**
 * Custom Exception handler
 * 
 * @author likevarun
 *
 */
public class CustomErrorType extends Exception{
	private static final long serialVersionUID = 1L;
	private String errorMessage;

    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
