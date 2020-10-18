package com.in28minutes.rest.webservices.restfulwebservice.helloWorld;

public class HelloWorldBean {

	private Object message;

	public HelloWorldBean(String message) {
		this.message = message;	
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}
	
	

//	public Object getMessage() {
//		return message;
//	}
//
//	public void setMessage(Object message) {
//		this.message = message;
//	}
//
//	@Override
//	public String toString() {
//		return "HelloWorldBean [message=" + message + "]";
//	}
	
	
	
	
	
		
}
