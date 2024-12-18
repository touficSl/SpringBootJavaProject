package com.tsspringexperience.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsspringexperience.entities.Hotel;
import com.tsspringexperience.entities.HotelServices;
import com.tsspringexperience.entities.ServiceAction;
import com.tsspringexperience.exception.NotAuthorizedException;
import com.tsspringexperience.multi.tenant.RequestContext;
import com.tsspringexperience.services.DeviceTypeService;
import com.tsspringexperience.services.HotelService;
import com.tsspringexperience.utils.Constants;
import com.tsspringexperience.utils.Credentials;
import com.tsspringexperience.utils.SystemResponse;
import com.tsspringexperience.utils.Utils;

public class Interceptor extends HandlerInterceptorAdapter { 

	@Autowired
	HotelService hotelService; 
	
	@Autowired
	DeviceTypeService deviceTypeService; 
	 

    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {  

    	/* For front end requests */
    	String path = request.getServletPath();
    	if(!Constants.NO_AUTH_API_LIST.contains(path) 
    			&& path.contains(Constants.API)) {
        	
        	/* Get customerId from the header, and check the multi-tenancy to identify client DB */ 
        	String authorization = request.getHeader(Constants.AUTHORIZATION);
        	
        	/* if no authorization in the header */
	        if (authorization == null || authorization.trim().equals("")) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.REQUIRED_HEADER_805, null, null, null)));
	    		return false;
	        }

        	/* Get bearer token from the header, and check the authentication NCS service */
        	String customerId = request.getHeader(Constants.CUSTOMERID);
        	
        	/* if no customerId in the header */
	        if (customerId == null || customerId.trim().equals("")) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.REQUIRED_CUSTOMER_807, null, null, null)));
	    		return false;
	        } 
        
	        String token = "";
	        final String token_type = Credentials.getAuthTokenType();
	        final String url = Credentials.getAuthUrl();  

	        /* check if token in the header exist */
	        if (!authorization.startsWith(token_type)) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.INVALID_HEADER_806, null, null, null)));
	    		return false;
	        } 

	        token = authorization.replace(token_type, "");
            
	        final RestTemplate restTemplate = new RestTemplate();
	        final HttpHeaders service_headers = new HttpHeaders();
	        String result = "";
	        final JSONObject jsonObj2 = new JSONObject();
	        jsonObj2.put("token", (Object)token);
	        
            service_headers.set("Content-Type", "application/json");
            final HttpEntity<?> entity = (HttpEntity<?>)new HttpEntity<Object>((Object)jsonObj2.toString(), (MultiValueMap<String, String>)service_headers);
            try {
            	result = (String)restTemplate.postForObject(url, (Object)entity, (Class<?>)String.class, new Object[0]);
            } catch (Exception e) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.ERROR_GENERATING_TOKEN_812, null, null, null)));
	    		return false;
			}
            
            JSONObject json = new JSONObject(result);
            
            if(!json.has("success") || !json.getBoolean("success")) {
	    		returnErrorResponse(response, new NotAuthorizedException(new ObjectMapper().readValue(json.toString(), SystemResponse.class)));
	    		return false;
	        } 
            else {
            	JSONObject data = json.getJSONObject("data");
            	String tokenCustomerId = data.getString("username");
            	if(!customerId.equals(tokenCustomerId)) {
    	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.INVALID_TOKEN_CUSTOMER_809, null, null, null)));
    	    		return false;
    	        } 
            } 

            RequestContext.setCustomerId(customerId);

	        /* Check HotelId, if can use the service and the request */
        	String HotelId = request.getHeader(Constants.HotelId);
	        if (HotelId != null && !HotelId.trim().equals(""))   
    			if (checkHotelAuthorization(response, HotelId, path) == false) /* Only if returned false */
    				return false;
	        
	        /* Check deviceTypeId */
			String deviceTypeId = request.getHeader(Constants.deviceTypeId);
			if(deviceTypeId == null || deviceTypeId.trim().equals("")) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.REQUIRED_DEVICE_TYPE_ID_117, null, null, null)));
	    		return false;
	        } 
			
			try {
				if(deviceTypeService.checkDeviceType(deviceTypeId) == null) {
		    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.INVALID_DEVICE_TYPE_ID_115, null, null, null)));
		    		return false;
		        } 
			} catch (Exception e) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.INVALID_DEVICE_TYPE_ID_115, "Error while validating deviceTypeId.", null, null)));
	    		return false;
			}
	    	
	    }
    	/* For dedge requests - we are not using it for now*/
    	/*else if(!Constants.NO_AUTH_API_LIST.contains(path) 
        			&& path.contains(Constants.DEDGE_API)) {
        }*/
    	
    	return true;
    }
    
    public boolean checkHotelAuthorization(HttpServletResponse response, String HotelId, String actionPath) throws JsonProcessingException, IOException, JSONException {  
				
		/* Check if HotelId exists in hotel table */
    	try {
			Hotel hotel = hotelService.getOne(HotelId);
			if(hotel == null) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.HOTEL_SERVICE_AUTH_810, "This Hotel with hotel Id = " + HotelId + " does not exist.", null, null)));
	    		return false;
			}
    	} catch (Exception e) {
    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.HOTEL_SERVICE_AUTH_810, "Error while checking hotel Id = " + HotelId + " if exists.", null, null)));
    		return false;
		}
			
		/* Check if HotelId exists in hotel_services table and if authorized */
    	try {
			HotelServices hotelServices = hotelService.findHotelServiceByHotelId(HotelId);
			if(hotelServices == null || !hotelServices.getService().getName().equals(Constants.SERVICE_NAME) || !hotelServices.isEnabled()) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.HOTEL_SERVICE_AUTH_811, "This Hotel with hotel Id = " + HotelId + " is not authorized to use " + Constants.SERVICE_NAME + " service.", null, null)));
	    		return false;
			}
    	} catch (Exception e) {
    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.HOTEL_SERVICE_AUTH_811, "Error while checking hotel Id = " + HotelId + " if authorized to use " + Constants.SERVICE_NAME + " service.", null, null)));
    		return false;
		}
			
		/* Check if HotelId exists in service_action table and if authorized to use this request */
    	try {
			ServiceAction serviceAction = hotelService.findServiceActionByHotelId(HotelId, actionPath);
			if(serviceAction == null) {
	    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.HOTEL_SERVICE_AUTH_811, "This Hotel with hotel Id = " + HotelId + " is not authorized to use " + actionPath + " request.", null, null)));
	    		return false;
			}
    	} catch (Exception e) {
    		returnErrorResponse(response, new NotAuthorizedException(Utils.GetJsonFormat(Constants.HOTEL_SERVICE_AUTH_811, "Error while checking hotel Id = " + HotelId + " if authorized to use " + actionPath + " request.", null, null)));
    		return false;
		}
    	
    	return true;
	}
    
    private void returnErrorResponse (HttpServletResponse response, Object object) throws JsonProcessingException, IOException {
		response.setContentType("application/json");
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		 
		response.getWriter().write(new ObjectMapper().writeValueAsString(object));
    }

    /**
     * Executed before after handler is executed
     **/
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
    }

    /**
     * Executed after complete request is finished
     **/
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
   		RequestContext.clear();
    }   
     
}
