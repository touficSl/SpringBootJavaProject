package com.tsspringexperience.rest.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.tsspringexperience.rest.errorhandler.CallsErrorHandler;
import com.tsspringexperience.rest.errorhandler.HandleError;
import com.tsspringexperience.rest.errorhandler.ProjectException;

public abstract class RestHandler implements HandleError { 
	private String protocol; // "https"
	private String host; //example.com
	private String path; // test/test
	private URI uri;
	private URL url;
	protected RestTemplate restTemplate = new RestTemplate();
	protected String body;
	protected HttpHeaders headers;
	protected ResponseEntity<String> result;
	protected String error = null;
	protected String query = null;
	protected MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	
	
	/**
	 * 
	 * @param protocol the protocol example https
	 * @param host the host example: example.com
	 * @param path the path after the host example: test/test/test
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	public RestHandler(String protocol, String host, String path) throws URISyntaxException, MalformedURLException {
		this.protocol = protocol;
		this.host = host;
		this.path ="/"+ path;
		this.uri = new URI(this.protocol, null, this.host, -1, this.path, null, null);
		this.url = uri.toURL();
	}	
	
	/**
	 * 
	 * @param protocol the protocol example https
	 * @param host the host example: example.com
	 * @param path the path after the host example: test/test/test
	 * @param query the get params & separated without the ?
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	public RestHandler(String protocol, String host, String path, String query) throws URISyntaxException, MalformedURLException {
		this.protocol = protocol;
		this.host = host;
		this.path ="/"+ path;
		this.uri = new URI(this.protocol, null, this.host, -1, this.path, query, null);
		this.url = uri.toURL();
	}
	
	/**
	 * initialises the restTemplate object
	 */
	protected void initRestTemplate() {
		this.restTemplate = new RestTemplate();
		ResponseErrorHandler errorHandler =new CallsErrorHandler(this);
		this.restTemplate.setErrorHandler(errorHandler);
	}
	
	/**
	 * 
	 * @returns the url for this class.
	 */
	public String getUrlAsString() {
		String path = url.toString();

		if (path.contains("%252C"))
			path = path.replace("%252C", ",");
		
		if (path.contains("%3F"))
			path = path.replace("%3F", "?");
		
		if(path.contains("%2540"))
			path = path.replace("%2540", "@");
		
		return path;
	}
	
	/**
	 * implementation of this class is used to construct the HttpHeaders headers object
	 * please note that headers is equal to null if on construction of the current object
	 */
	public abstract void constructHeaders();
	/**
	 * implementation of this class is used to construct the HashMap<Object, Object> body object
	 * please note that body is equal to null if on construction of the current object
	 */
	public abstract void constructBody();
	
	
	public String callAsFormUrlEncodedPost() throws ProjectException {
		this.initRestTemplate();
		this.constructHeaders();
		this.constructBody();

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		this.result = restTemplate.exchange(this.getUrlAsString(), HttpMethod.POST, entity, String.class);
		return result.getBody();
	}
	
	/**
	 * executes the call built in the class as a post call 
	 * @return the response body from the call as string
	 * @throws ProjectException 
	 */
	public String callAsPost() throws ProjectException {
		this.initRestTemplate();
		this.constructHeaders();
		this.constructBody();
		
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
			}
		});

		HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
		this.result = restTemplate.exchange(this.getUrlAsString(), HttpMethod.POST, entity, String.class);
		if(error != null) {
			System.out.println("SERVER ERROR>> " + error);
			return error;
		}
		return result.getBody();
	}
	 
	/**
	 * executes the call built in the class as a put call 
	 * @return the response body from the call as string
	 * @throws ProjectException 
	 */
	public String callAsPut() throws ProjectException {
		this.initRestTemplate();
		this.constructHeaders();
		this.constructBody();

		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
			}
		});

		HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
		this.result = restTemplate.exchange(this.getUrlAsString(), HttpMethod.PUT, entity, String.class);
		if(error != null) {
			System.out.println("SERVER ERROR>> " + error);
			return error;
		}
		return result.getBody();
	}
	 
	/**
	 * executes the call built in the class as a delete call 
	 * @return the response body from the call as string
	 * @throws ProjectException 
	 */
	public String callAsDelete() throws ProjectException {
		this.initRestTemplate();
		this.constructHeaders();
		this.constructBody();

		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
			}
		});
		
		HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
		this.result = restTemplate.exchange(this.getUrlAsString(), HttpMethod.DELETE, entity,
				String.class);
		if(error != null) {
			System.out.println("SERVER ERROR>> " + error);
			return error;
		}
		return result.getBody();
	}
	
	/**
	 * executes the call built in the class as a get call 
	 * @return the response body from the call as string
	 * @throws ProjectException 
	 */
	public String callAsGet() throws ProjectException {
		this.constructHeaders();
		this.initRestTemplate();

		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
			}
		});

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		this.result = restTemplate.exchange(this.getUrlAsString(), HttpMethod.GET, entity,
				String.class);
		if(error != null) {
			System.out.println("SERVER ERROR>> " + error);
			return error;
		}
		return result.getBody();
	}

	@Override
	public void handle(String error){
		this.error  = error;
	}

	public MultiValueMap<String, String> getMap() {
		return map;
	}

	public void setMap(MultiValueMap<String, String> map) {
		this.map = map;
	}
	
	public String getError() {
		return this.error;
	}
}
