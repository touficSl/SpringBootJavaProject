package com.tsspringexperience.multi.tenant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.tsspringexperience.entities.NCICustomerEntity;
import com.tsspringexperience.services.NCICustomerService;
import com.tsspringexperience.utils.Credentials;
import com.tsspringexperience.utils.NCIResponse;

@Component
public class TenantDataSource implements Serializable { 
  
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@PostConstruct
    public static Map<Object, Object> getDataSourceHashMap() {
		NCIResponse nciResponse = NCICustomerService.getCustomers(Credentials.isDebugMode(), Credentials.getCommonDatabaseUrl(), Credentials.getCommonDatabaseUser(), Credentials.getCommonDatabaseDriver(), Credentials.getCommonDatabasePassword(), Credentials.getCommonDatabaseCatalog()); 
		List<NCICustomerEntity> clientDetails = (List<NCICustomerEntity>) nciResponse.getData();
        Map<Object, Object> result = new HashMap<>();
        for (NCICustomerEntity nciCustomerEntity : clientDetails) {

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(nciCustomerEntity.getDatabaseDriver());
            dataSource.setUrl(nciCustomerEntity.getDatabaseUrl());
            dataSource.setUsername(nciCustomerEntity.getDatabaseUser());
            dataSource.setPassword(nciCustomerEntity.getDatabasePassword());
        	 
            result.put(nciCustomerEntity.getCustomerId(), dataSource);
        }
        return result;
    } 

}