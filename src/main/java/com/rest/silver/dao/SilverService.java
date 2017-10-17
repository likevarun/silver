package com.rest.silver.dao;

import com.rest.silver.model.SilverPojo;
import com.rest.silver.util.CustomErrorType;

public interface SilverService {
	
	public Silver checkUser(String requestHeader);
	
	public String getState(String userId);

	public int getSum(String userId);
	
	public String getChars(String userId);
	
	public Silver postState(String userId, SilverPojo s) throws CustomErrorType, Exception;
	
	public boolean deleteChars(String userId, char c) throws CustomErrorType;
	
	public Silver fetchUser(String userId);
	
	public Silver createUser(String userId) ;
}
