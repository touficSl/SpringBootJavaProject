package com.tsspringexperience.utils;

import java.util.ArrayList;
import java.util.List;

public final class Constants {

	public static final String BACKEND_VERSION = "1.3.7";

	public static final String SERVICE_NAME = "dedge";


	/* List of APIs (url) that don't need authentication */
	public static List<String> NO_AUTH_API_LIST = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("/error");	// first-time-run 

			/* Swagger */
			add("/swagger"); 
			add("v3/api-docs");
		}
	};
	
	public static String API = "/api/v1/";

	public static String PRICEMODEL_PER_DAY = "PerDay";
	public static String PRICEMODEL_PER_PERSON = "PerPerson";
	public static String PRICEMODEL_PER_OCCUPANCY = "PerOccupancy";
	
	/* API call Constants */  
	public static final String AUTHORIZATION = "Authorization";
	public static final String CUSTOMERID = "customerId";
	public static final String HotelId = "HotelId";
	public static final String deviceTypeId = "deviceTypeId";

	public static final String uniqueId = "uniqueId";

	public static final String HTTPS_PROTOCOL = "https";
	public static final String TOKEN_TYPE_BEARER = "Bearer "; 
	
	public static final String DateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DateFormat = "yyyy-MM-dd";
	public static final String CCDateFormat = "MM/yy";

	public static final int SUCCESS = 0;
	public static final int WARNING = -1;
	public static final int ERROR = -2;
	public static final int SERVER_OK = 200;
	
	public static final int REQUIRED_HEADER_805 = 805;
	public static final int INVALID_HEADER_806 = 806;
	public static final int REQUIRED_CUSTOMER_807 = 807;
	public static final int INVALID_CUSTOMER_808 = 808;
	public static final int INVALID_TOKEN_CUSTOMER_809 = 809;
	public static final int HOTEL_SERVICE_AUTH_810 = 810;
	public static final int HOTEL_SERVICE_AUTH_811 = 811;
	public static final int ERROR_GENERATING_TOKEN_812 = 812; 

	public static final int INVALID_DEVICE_TYPE_ID_115 = 115;
	public static final int REQUIRED_DEVICE_TYPE_ID_117 = 117;
	public static final int INVALID_CREDENTIALS_120 = 120;
	public static final int BOOKING_NOT_SAVED_121 = 121;
	
	public static final String ERROR_GENERATING_TOKEN = "Error while generating token, please try again.";
	public static final String REQUIRED_HEADER = "Required authorization.";
	public static final String INVALID_HEADER = "Invalid header.";
	public static final String REQUIRED_CUSTOMER = "Missing request header 'customerId' for method parameter of type String.";
	public static final String INVALID_CUSTOMER = "Invalid customerId";
	public static final String INVALID_TOKEN_CUSTOMER = "Invalid token customerId.";
	public static final String REQUIRED_DEVICE_TYPE_ID = "Missing request header 'deviceTypeId' for method parameter of type String.";
	public static final String INVALID_DEVICE_TYPE_ID = "Invalid deviceTypeId.";
	public static final String SERVER_ERROR_MSG = "Error";
	public static final String SERVER_SUCCESS_MSG = "Success";
	public static final String REQUIRED_HOTELCODE = "hotelCode is required.";
	public static final String REQUIRED_LOGIN = "login is required.";
	public static final String REQUIRED_PASSWORD = "password is required.";
	public static final String INVALID_CREDENTIALS = "Invalid credentials.";
	public static final String INVALID_DATE_FORMAT = "Invalid date format, should be '" + DateTimeFormat + "'";
	public static final String INVALID_TO_FROM_DATE = "From date should be less than To date.";
	public static final String REQUIRED_FROM_TO_DATES_OR_DURATION = "Required (from/to dates or duration).";
	public static final String DURATION_VALUE_CHECK = "Duration must be greater than 1";
	public static final String BOOKING_NOT_SAVED = "Booking not saved, kindly retry or contact  support.";

	/* D-edge error structure*/
	public static final int INVALID_HOTELCODE_1 = 1;
	public static final int BAD_AUTHENTICATION_3 = 3;
	public static final int ROOM_MAPPING_ERROR_4 = 4;
	public static final int RATE_MAPPING_ERROR_5 = 5;
	public static final int ROOM_RATE_MAPPING_ERROR_6 = 6;
	public static final int RATE_DERIVATION_WARNING_7 = 7;
	public static final int MISSING_OCCUPANCY_ERROR_11 = 11;
	public static final int INVALID_BOOKING_ACTION_12 = 12;
	
	public static final int NO_ROOMS_14 = 14;
	public static final int NO_RATES_15 = 15;
	public static final int INTERNAL_ERROR_203 = 203;
	public static final int INTERNAL_ERROR_204 = 204;
	public static final int INTERNAL_ERROR_205 = 205;
	public static final int INTERNAL_ERROR_206 = 206;
	public static final int INTERNAL_ERROR_207 = 207;
	public static final int INTERNAL_ERROR_208 = 208;
	public static final int INTERNAL_ERROR_209 = 209;
	
	public static final String INVALID_HOTELCODE_ERROR = "HotelNotLinked";
	public static final String BAD_AUTHENTICATION_ERROR = "BadAuthentication";
	public static final String INTERNAL_ERROR = "InternalError";
	public static final String NO_ROOMS_ERROR = "NoRooms";
	public static final String NO_RATES_ERROR = "NoRates";
	public static final String ROOM_MAPPING_ERROR = "RoomMappingError";
	public static final String RATE_MAPPING_ERROR = "RateMappingError";
	public static final String ROOM_RATE_MAPPING_ERROR = "RoomRateMapping";
	public static final String RATE_DERIVATION_WARNING = "RateDerivation";
	public static final String MISSING_OCCUPANCY_ERROR = "MissingOccupancy";
	
	public static final String login = "login";
	public static final String password = "password";
	public static final String hotelCode = "hotelCode";
	

	public static final String ENCRYPTION_KEY = "KbPlSkVm4";

	public static final String LOG_PATH = "/tsl/midd_data/portal/#customer#/logs/dedge/";

	public static final String DEFAULT_CURRENCY = "EUR";

	public static final String BOOKINGS_FILE = "Bookings";

	public static final String DEDGEREQUESTS_FILE = "DedgeRequests";

	public static final String FRONTREQUESTS_FILE = "FrontRequests";

	public static final CharSequence TSL_MIDDATA_PATH = "/tsl/midd_data";
	
}