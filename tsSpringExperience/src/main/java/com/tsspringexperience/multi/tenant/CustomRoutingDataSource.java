package com.tsspringexperience.multi.tenant;


 
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.tsspringexperience.utils.Credentials;
 
public class CustomRoutingDataSource extends AbstractRoutingDataSource {
	
    @Override
    protected Object determineCurrentLookupKey() { 
    	
    	return RequestContext.getCustomerId() != null ? RequestContext.getCustomerId() : Credentials.getDefaultCustomerId();
    }
}