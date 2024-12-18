package com.tsspringexperience.rest.apis;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.tsspringexperience.rest.base.RestHandler;
import com.tsspringexperience.utils.Constants;

public class SaveBookingPostCall extends RestHandler{ 
	
	private String token;
	
	/**
	 * 
	 * @param protocol the protocol example https
	 * @param host the host example: example.com
	 * @param path the path after the host example: test/test/test 
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	public SaveBookingPostCall(String protocol, String host, String path, String body, String token) throws URISyntaxException, MalformedURLException {
		super(protocol, host, path); 
		this.body = body;
		this.token = token;
		
	} 
	 
	@Override
	public void constructHeaders() {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);
		this.headers.set("Authorization", Constants.TOKEN_TYPE_BEARER + token);
			
	}

	@Override
	public void constructBody() { 
		
	}  

}
