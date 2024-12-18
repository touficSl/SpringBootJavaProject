package com.tsspringexperience;

import com.tsspringexperience.services.NCICustomerService;
import com.tsspringexperience.utils.NCIResponse;

public class ManagementApplication {

	public static void main(String[] args) {
		testConnection();
	}
	
	/** 
	 * You can test your connection using this method, just uncomment it and import the required classes (ctrl + shift + o) and fill your 
	 * database connection then run/debug ManagementApplication class 
	 **/
	private static void testConnection() {

		NCIResponse response = NCICustomerService.getCustomerByCustomerId(
				"87ee421566de79298d5d7ee5bac20d379edc8549c17ea408942efd7fa0e4768e", 							// customerId 
				true,
				"jdbc:mysql://localhost:3306/common?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true",   // databaseUrl
				"root",							// databaseUser 
				"com.mysql.jdbc.Driver", 		// databaseDriver
				"toor",							// databasePassword 
				"common"						// databaseCatalog
				);

		System.out.println("Test your connection using this method >>>>>>> " + response.isSuccess());
		if(response.isSuccess()) {
			System.out.println("API Version >>>>>>> " + response.getApiVersion());
			System.out.println("Data >>>>>>> " + response.getData());
		}
		
		response = NCICustomerService.getCustomers(
				false,
				"jdbc:mysql://localhost:3306/common?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true",   // databaseUrl
				"root",							// databaseUser 
				"com.mysql.jdbc.Driver", 		// databaseDriver
				"toor",							// databasePassword 
				"common"						// databaseCatalog
				);

		System.out.println("Test your connection using this method >>>>>>> " + response.isSuccess());
		if(response.isSuccess())
			System.out.println("Data >>>>>>> " + response.getData());
	}
}
