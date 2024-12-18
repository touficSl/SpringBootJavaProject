package com.tsspringexperience.multi.tenant;

public class RequestContext {

	private static ThreadLocal<String> currentCustomerId = new ThreadLocal<>(); 

	public static String getCustomerId() {
		return currentCustomerId.get();
	}

	public static void setCustomerId(String customerId) {
		currentCustomerId.set(customerId);
	} 
 
	public static void clear(){
		currentCustomerId.remove();
	}
}