package com.in28minutes.rest.webservices.restfulwebservice.shopUntilDrop;

public class ShopUntilDropBean {
	
	private Object message;
	
	public ShopUntilDropBean(String message) {
		this.message = message;	
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
}
