package com.rest.silver.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Silver")
public class Silver {
	@Id
	private String userId;
	private String state;
	
	public Silver(){
		
	}
	public Silver(String userId, String state) {
		this.userId=userId;
		this.state=state;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
