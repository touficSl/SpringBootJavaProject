package com.tsspringexperience.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tsspringexperience.entities.NCICustomerEntity;
import com.tsspringexperience.utils.ConsoleLog;
import com.tsspringexperience.utils.Constants;
import com.tsspringexperience.utils.NCIResponse;

public class CustomerRepository {
	
	private String url;
	private String user;
	private String password;
	private String catalog;
	private String driver;
	
	public CustomerRepository(String url, String user, String driver,String password, String catalog) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.catalog = catalog;
		this.driver = driver;
		ConsoleLog.setCatalog(catalog);
	} 
	
	public NCIResponse getCustomers(boolean printLog) { 
		try {
			Class.forName(driver);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password)){
			connection.setCatalog(catalog);

			String property = "SELECT * FROM customer WHERE name <> 'common'";
			ConsoleLog.print(property, printLog);
			try (PreparedStatement statement = connection.prepareStatement(property)){
				ResultSet result = statement.executeQuery();
				List<NCICustomerEntity> customers = new ArrayList<NCICustomerEntity>();
				while(result.next()) {  
					customers.add(new NCICustomerEntity(result));
				}
				return new NCIResponse(true, Constants.SUCCESS, "Customers found.", customers);
			} catch (SQLException e) {
				e.printStackTrace();
				ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
				return new NCIResponse(false, Constants.SQL_STATEMENT_ERROR, e.getMessage(), "");
			} catch (Exception e) {
				e.printStackTrace();
				ConsoleLog.printError("Statement exception : " + e.toString());
				return new NCIResponse(false, Constants.EXCEPTION_ERROR, e.getMessage(), "");
			}
		} catch (SQLException e) {
			ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
			return new NCIResponse(false, Constants.DATABASE_CONNECTION_ERROR, e.getMessage(), "");
		}
		
	} 
	
	public NCIResponse getCustomerByCustomerId(String customerId, boolean printLog) {

		try {
			Class.forName(driver);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password)){
			connection.setCatalog(catalog);

			String property = "SELECT * FROM customer where customer_id = ?";
			ConsoleLog.print(property, printLog);
			try (PreparedStatement statement = connection.prepareStatement(property)){
				statement.setString(1, customerId);
				ResultSet result = statement.executeQuery();
				
				NCICustomerEntity customer = new NCICustomerEntity();
				int count = 0;
				while(result.next()) { 
					customer = new NCICustomerEntity(result);
					count++;
				}
				if(count == 0) 
					return new NCIResponse(false, Constants.CUSTOMER_NOT_FOUND, "No Customer found with customer id = " + customerId, new Object());
				else if (count == 1) 
					return new NCIResponse(true, Constants.SUCCESS, "Customer found.", customer);
				
				return new NCIResponse(false, Constants.MANY_CUSTOMER_FOUND, "Multiple Customer found with customer id = " + customerId, "");
				
			} catch (SQLException e) {
				e.printStackTrace();
				ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
				return new NCIResponse(false, Constants.SQL_STATEMENT_ERROR, e.getMessage(), "");
			} catch (Exception e) {
				e.printStackTrace();
				ConsoleLog.printError("Statement exception : " + e.toString());
				return new NCIResponse(false, Constants.EXCEPTION_ERROR, e.getMessage(), "");
			}
		} catch (SQLException e) {
			ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
			return new NCIResponse(false, Constants.DATABASE_CONNECTION_ERROR, e.getMessage(), "");
		}
	}

	public NCIResponse getCustomerByCustomerName(String customerName, boolean printLog) {
		try {
			Class.forName(driver);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password)){
			connection.setCatalog(catalog);

			String property = "SELECT * FROM customer where name = ?";
			ConsoleLog.print(property, printLog);
			try (PreparedStatement statement = connection.prepareStatement(property)){
				statement.setString(1, customerName);
				ResultSet result = statement.executeQuery();
				
				NCICustomerEntity customer = new NCICustomerEntity();
				int count = 0;
				while(result.next()) { 
					customer = new NCICustomerEntity(result);
					count++;
				}
				if(count == 0) 
					return new NCIResponse(false, Constants.CUSTOMER_NOT_FOUND, "No Customer found with name = " + customerName, new Object());
				else if (count == 1) 
					return new NCIResponse(true, Constants.SUCCESS, "Customer found.", customer);
				
				return new NCIResponse(false, Constants.MANY_CUSTOMER_FOUND, "Multiple Customer found with name = " + customerName, "");
				
			} catch (SQLException e) {
				e.printStackTrace();
				ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
				return new NCIResponse(false, Constants.SQL_STATEMENT_ERROR, e.getMessage(), "");
			} catch (Exception e) {
				e.printStackTrace();
				ConsoleLog.printError("Statement exception : " + e.toString());
				return new NCIResponse(false, Constants.EXCEPTION_ERROR, e.getMessage(), "");
			}
		} catch (SQLException e) {
			ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
			return new NCIResponse(false, Constants.DATABASE_CONNECTION_ERROR, e.getMessage(), "");
		}
	}

	public NCIResponse getCustomerByCredentials(String login, String customerPassword, boolean printLog) {
		try {
			Class.forName(driver);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password)){
			connection.setCatalog(catalog);

			String property = "SELECT * FROM customer where login = ? and password = AES_ENCRYPT(?, ?)";
			ConsoleLog.print(property, printLog);
			try (PreparedStatement statement = connection.prepareStatement(property)){
				statement.setString(1, login);
				statement.setString(2, customerPassword);
				statement.setString(3, Constants.ENCRYPTION_KEY);
				ResultSet result = statement.executeQuery();
				
				NCICustomerEntity customer = new NCICustomerEntity();
				int count = 0;
				while(result.next()) { 
					customer = new NCICustomerEntity(result);
					count++;
				}
				if(count == 0) 
					return new NCIResponse(false, Constants.CUSTOMER_NOT_FOUND, "No Customer found with login = " + login + ", and password = " + customerPassword, new Object());
				else if (count == 1) 
					return new NCIResponse(true, Constants.SUCCESS, "Customer found.", customer);
				
				return new NCIResponse(false, Constants.MANY_CUSTOMER_FOUND, "Multiple Customer found.");
				
			} catch (SQLException e) {
				e.printStackTrace();
				ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
				return new NCIResponse(false, Constants.SQL_STATEMENT_ERROR, e.getMessage(), "");
			} catch (Exception e) {
				e.printStackTrace();
				ConsoleLog.printError("Statement exception : " + e.toString());
				return new NCIResponse(false, Constants.EXCEPTION_ERROR, e.getMessage(), "");
			}
		} catch (SQLException e) {
			ConsoleLog.printError("Unable to open a database connection due to : " + e.toString());
			return new NCIResponse(false, Constants.DATABASE_CONNECTION_ERROR, e.getMessage(), "");
		}
	}
}
