package com.tsspringexperience.rest.apis;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.tsspringexperience.rest.base.RestHandler;
import com.tsspringexperience.utils.Constants;

public class FormUrlEncodedPostCalls extends RestHandler{ 
	 
	private String token;
	
	/**
	 * 
	 * @param protocol the protocol example https
	 * @param host the host example: example.com
	 * @param path the path after the host example: test/test/test 
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	public FormUrlEncodedPostCalls(String protocol, String host, String path, MultiValueMap<String, String> map, String token) throws URISyntaxException, MalformedURLException {
		super(protocol, host, path); 
		this.map = map;
		this.token = token;
		
	} 
	 
	@Override
	public void constructHeaders() {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);
		this.headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		this.headers.set("Authorization", Constants.TOKEN_TYPE_BEARER + token);
			
	}

	@Override
	public void constructBody() { 
		
	}  

}
