package com.tsspringexperience.utils;

import java.util.Properties;

public class Credentials {
	
	public Credentials() {
	}
	
	static boolean isUat = false;

	static Properties properties = isUat ? PropertiesManager.load("/applicationuat.properties") : PropertiesManager.load("/application.properties");

	
	/* Common DB section */

	public static String getCommonDatabaseUrl() {
		return properties.getProperty("common.database.url");
	}

	public static String getCommonDatabaseCatalog() {
		return properties.getProperty("common.database.catalog");
	}

	public static String getCommonDatabaseUser() {
		return properties.getProperty("common.database.user");
	}

	public static String getCommonDatabasePassword() {
		return properties.getProperty("common.database.password");
	}

	public static String getCommonDatabaseDriver() {
		return properties.getProperty("common.database.driver");
	}

	
	/* Others */
	public static String getDefaultCustomerId() {
		return properties.getProperty("default.customer.id");
	}
	
	public static String isEmailStarttlsEnable() {
		return properties.getProperty("email.is.starttls.enabled");
	}

	public static String getEmailHost() {
		return properties.getProperty("email.host");
	}

	public static Object getEmailPort() {
		return properties.getProperty("email.port");
	}

	public static String getEmailUser() {
		return properties.getProperty("email.user");
	}

	public static String getEmailPassword() {
		return properties.getProperty("email.password");
	}

	public static String getEmailSender() {
		return properties.getProperty("email.sender");
	}

	public static String getReceiver(String addressNumber) {
		return properties.getProperty("email.receiver." + addressNumber);
	}

	public static boolean applyRemoveOldRequestLogs() {
		return properties.getProperty("remove.old.request.logs").equals("true") ? true : false;
	}

	public static boolean isDebugMode() {
		return properties.getProperty("is.debug.mode").equals("true") ? true : false;
	}

	/*	Auth section */
	
	public static String getAuthTokenType() {
		return properties.getProperty("auth.token.type") + " ";
	}
	
	public static String getAuthUrl() {
		return properties.getProperty("auth.url");
	}
	
	public static String getSDAServiceUrl() {
		return properties.getProperty("sda.service.url");
	}
	
	public static String getSDASaveBookingPath() {
		return properties.getProperty("sda.service.save.booking.path");
	}
	
	public static String getSDAUpdateBookingPath() {
		return properties.getProperty("sda.service.update.booking.path");
	}
	
	public static String getSDACancelBookingPath() {
		return properties.getProperty("sda.service.cancel.booking.path");
	}
	
	public static String getSDABookingDeviceTypeId() {
		return properties.getProperty("sda.service.booking.deviceTypeId");
	}
	
	public static String getAvailProConnectPushBookingUrl() {
		return properties.getProperty("availproconnect.push.booking.url")
				.replace("#LOGIN#", getAvailProConnectPushBookingLogin())
				.replace("#PASS#", getAvailProConnectPushBookingPassword());
	}
	
	public static String getAvailProConnectPushBookingLogin() {
		return properties.getProperty("availproconnect.push.booking.login");
	}
	
	public static String getAvailProConnectPushBookingPassword() {
		return properties.getProperty("availproconnect.push.booking.password");
	}

	public static Boolean saveFileSystemLogs() {
		return Boolean.valueOf(properties.getProperty("save.file.logs"));
	}

	public static String getDedgeBookingEmailPath() {
		return properties.getProperty("dedge.booking.email.path");
	}
}
