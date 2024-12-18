package com.tsspringexperience.services;

import com.tsspringexperience.repositories.CustomerRepository;
import com.tsspringexperience.utils.NCIResponse;

public class NCICustomerService {
	final static int NCI_SUCCESS = 101;
	final static int NCI_UNKNOWN_ERROR = 102;
	final static int NCI_CUSTOMER_NOT_FOUND = 112;
	final static int NCI_CUSTOMER_MANY_FOUND = 113;
	
	
	public static NCIResponse getCustomerByCustomerId(String customerId, boolean printLog, String databaseUrl, String databaseUser, String databaseDriver, String databasePassword, String databaseCatalog) {
		return new CustomerRepository(databaseUrl, databaseUser, databaseDriver, databasePassword, databaseCatalog).getCustomerByCustomerId(customerId, printLog);
	}
	
	public static NCIResponse getCustomers(boolean printLog, String databaseUrl, String databaseUser, String databaseDriver, String databasePassword, String databaseCatalog) { 
		return new CustomerRepository(databaseUrl, databaseUser, databaseDriver, databasePassword, databaseCatalog).getCustomers(printLog);
	} 
	
	public static NCIResponse getCustomerByCustomerName(String customerName, boolean printLog, String databaseUrl, String databaseUser, String databaseDriver, String databasePassword, String databaseCatalog) {
		return new CustomerRepository(databaseUrl, databaseUser, databaseDriver, databasePassword, databaseCatalog).getCustomerByCustomerName(customerName, printLog);
	}
	
	public static NCIResponse getCustomerByCredentials(String login, String password, boolean printLog, String databaseUrl, String databaseUser, String databaseDriver, String databasePassword, String databaseCatalog) {
		return new CustomerRepository(databaseUrl, databaseUser, databaseDriver, databasePassword, databaseCatalog).getCustomerByCredentials(login, password, printLog);
	}

}
