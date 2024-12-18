package com.tsspringexperience.config;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.tsspringexperience.entities.NCICustomerEntity;
import com.tsspringexperience.multi.tenant.RequestContext;
import com.tsspringexperience.services.NCICustomerService;
import com.tsspringexperience.utils.Constants;
import com.tsspringexperience.utils.Credentials;
import com.tsspringexperience.utils.LoggingUtils;
import com.tsspringexperience.utils.NCIResponse;

@Component 
public class CidFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(CidFilter.class);

	String customerName = null, fileName = null;
	
    @SuppressWarnings("serial")
	@Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
	
		try {
//				 call filter(s) upstream for the real processing of the request
	         filterChain.doFilter(wrappedRequest, wrapperResponse);
		} finally {
			
			customerName = null;
			fileName = Constants.FRONTREQUESTS_FILE;
	    	/* For front end requests */
	    	String path = request.getServletPath();
	    	if(!Constants.NO_AUTH_API_LIST.contains(path) 
	    			&& path.contains(Constants.API)) {

	        	String customerId = request.getHeader(Constants.CUSTOMERID);

	    		final NCIResponse nciResponse = NCICustomerService.getCustomerByCustomerId(customerId, Credentials.isDebugMode(), Credentials.getCommonDatabaseUrl(), Credentials.getCommonDatabaseUser(), Credentials.getCommonDatabaseDriver(), Credentials.getCommonDatabasePassword(), Credentials.getCommonDatabaseCatalog());
	    		NCICustomerEntity clientDetails = (NCICustomerEntity) nciResponse.getData();
	            customerName = clientDetails.getName();
	            fileName = Constants.FRONTREQUESTS_FILE;
	    	}

	    	try {
	        	String params = "";
		    	String requestBody = "";
		    	String responseBody = "";

		        params = getParameters(wrappedRequest);
		        
		        requestBody = params.equals("?") ? "--NO PARAMS ----- \n" : "PARAMS ----------- \n" + params + "\n--------\n\n";

		        byte[] buf = wrappedRequest.getContentAsByteArray();
		        if (buf.length > 0) {
		            try {
		                requestBody += new String(buf, 0, buf.length, wrappedRequest.getCharacterEncoding());
		            } catch (Exception e) {
		                logger.info("error in reading request body");
		            }
		        }
		        byte[] buf1 = wrapperResponse.getContentAsByteArray();
		        if (buf1.length > 0) {
		            try {
		                responseBody = new String(buf1, 0, buf1.length, wrapperResponse.getCharacterEncoding());
		            } catch (Exception e) {
		            	logger.info("error in reading response body");
		            }
		        }

		        if (customerName != null && fileName != null)
		        	LoggingUtils.logging(null, requestBody, responseBody, new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()), new ArrayList<String>() { { add(Constants.LOG_PATH.replace("#customer#", customerName)); } }, fileName);

	    	}catch (Exception e) {
			}
		        
		    wrapperResponse.copyBodyToResponse();
		}
    }
    
    private String getParameters(final HttpServletRequest request) {
        final StringBuffer posted = new StringBuffer();
        final Enumeration<?> e = request.getParameterNames();
        if (e != null)
            posted.append("?");
        while (e != null && e.hasMoreElements()) {
            if (posted.length() > 1)
                posted.append("&");
            final String curr = (String) e.nextElement();
            posted.append(curr).append("=");
            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }

//        final String ip = request.getHeader("X-FORWARDED-FOR");
//        final String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
//        if (!Strings.isNullOrEmpty(ipAddr))
//            posted.append("&_psip=" + ipAddr);
        return posted.toString();
    }
    
    @Override
    public void destroy() {
    	RequestContext.clear();
    	logger.info("destroy filter");
    }
}