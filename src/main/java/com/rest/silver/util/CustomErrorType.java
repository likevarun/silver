package com.rest.silver.util;


public class CustomErrorType extends Exception{

    /**
	 * Custom Exception handler
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;

    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
