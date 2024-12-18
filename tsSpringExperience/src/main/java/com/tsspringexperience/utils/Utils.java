package com.tsspringexperience.utils;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tsspringexperience.entities.Hotel;
import com.tsspringexperience.entities.HotelServices;
import com.tsspringexperience.entities.NCICustomerEntity;
import com.tsspringexperience.jaxb.ObjectFactory;
import com.tsspringexperience.jaxb.Response;
import com.tsspringexperience.jaxb.UpdateInventoryRequest;
import com.tsspringexperience.jaxb.Response.Failure;
import com.tsspringexperience.jaxb.Response.Warning;
import com.tsspringexperience.multi.tenant.RequestContext;
import com.tsspringexperience.services.HotelService;
import com.tsspringexperience.services.NCICustomerService;

public class Utils {
	
	static String customerName = null;

	private static Map<Integer, String> Messages = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(  Constants.SUCCESS, "" );
			put(  Constants.ERROR_GENERATING_TOKEN_812, Constants.ERROR_GENERATING_TOKEN );
			
			put(  Constants.REQUIRED_HEADER_805, Constants.REQUIRED_HEADER );
			put(  Constants.INVALID_HEADER_806, Constants.INVALID_HEADER );
			put(  Constants.REQUIRED_CUSTOMER_807, Constants.REQUIRED_CUSTOMER );
			put(  Constants.INVALID_CUSTOMER_808, Constants.INVALID_CUSTOMER );
			put(  Constants.INVALID_TOKEN_CUSTOMER_809, Constants.INVALID_TOKEN_CUSTOMER );
			put(  Constants.REQUIRED_DEVICE_TYPE_ID_117, Constants.REQUIRED_DEVICE_TYPE_ID );
			put(  Constants.INVALID_DEVICE_TYPE_ID_115, Constants.INVALID_DEVICE_TYPE_ID ); 
			put(  Constants.INVALID_CREDENTIALS_120, Constants.INVALID_CREDENTIALS ); 
			put(  Constants.INVALID_HOTELCODE_1, Constants.INVALID_HOTELCODE_ERROR ); 
			put(  Constants.BAD_AUTHENTICATION_3, Constants.BAD_AUTHENTICATION_ERROR ); 
			put(  Constants.INTERNAL_ERROR_203, Constants.INTERNAL_ERROR ); 
			put(  Constants.NO_ROOMS_14, Constants.NO_ROOMS_ERROR ); 
			put(  Constants.NO_RATES_15, Constants.NO_RATES_ERROR ); 
			put(  Constants.ROOM_MAPPING_ERROR_4, Constants.ROOM_MAPPING_ERROR ); 
			put(  Constants.RATE_MAPPING_ERROR_5, Constants.RATE_MAPPING_ERROR ); 
			put(  Constants.ROOM_RATE_MAPPING_ERROR_6, Constants.ROOM_RATE_MAPPING_ERROR ); 
			put(  Constants.RATE_DERIVATION_WARNING_7, Constants.RATE_DERIVATION_WARNING ); 
			put(  Constants.MISSING_OCCUPANCY_ERROR_11, Constants.MISSING_OCCUPANCY_ERROR ); 
			put(  Constants.BOOKING_NOT_SAVED_121, Constants.BOOKING_NOT_SAVED ); 
			
		}
	};

	public static SystemResponse GetJsonFormat(int status, String message, String data, String cId, String userMessage) throws JSONException { 
		
		SystemResponse systemResponse = new SystemResponse();
		systemResponse = GetJsonFormat(status, message, data, cId);
		
		for (SystemError systemError : systemResponse.getErrors())
			systemError.setUserMessage(userMessage);
		
		return systemResponse;
	}

	/**
	 * Return messsage with status
	 * @param status
	 * @return
	 * @throws JSONException 
	 */
	public static SystemResponse GetJsonFormat(int status, String message, String data, String cId) throws JSONException { 
		List<SystemError> errors = new ArrayList<SystemError>();
		if(message == null || message.isEmpty()) 
			if(!Messages.containsKey(status)) message = Messages.get(-1);
			else message = Messages.get(status);

		SystemResponse systemResponse = new SystemResponse();

		if(status != 0) {
			String errMsg = message;
			if(cId != null) errMsg = message + " CID: " + cId;
			SystemError error = new SystemError(errMsg, status+"", message);
			errors.add(error);
			systemResponse.setErrors(errors);
		}
		
		boolean isSuccess = false;
		
		systemResponse.setApiVersion(Constants.BACKEND_VERSION);
		
		if(data == null) {
			systemResponse.setData(new EmptyJsonResponse());
			if(status == Constants.SUCCESS) isSuccess = true;
		}
		else if(data.charAt(0) == '[') {
			isSuccess = true;
			JSONArray jsonArray = new JSONArray(data);
			systemResponse.setData(jsonArray.toString());
		}
		else {
			isSuccess = true;
			JSONObject response = new JSONObject(data);
			errors = new ArrayList<SystemError>();

			systemResponse.setData(response.toString());
		}

		systemResponse.setErrors(errors);
		systemResponse.setSuccess(isSuccess);
		if(isSuccess) systemResponse.setStatusCode(Constants.SERVER_OK);
		else systemResponse.setStatusCode(Constants.INTERNAL_ERROR_203);

		if(isSuccess) systemResponse.setMessage(Constants.SERVER_SUCCESS_MSG + ".");
		else systemResponse.setMessage(Constants.SERVER_ERROR_MSG + ".");
		return systemResponse;
	} 

	/* If Warning, status will be taken from warningStatus variable (Messages.get(warningStatus)) */
	public static Response GetXmlFormat(int status, String message, String comment, String cId, Integer warningStatus) {
		
		final ObjectFactory factory = new ObjectFactory();
		Response response = factory.createResponse();

		if(message == null || message.isEmpty()) 
			if(!Messages.containsKey(status)) message = Messages.get(-1);
			else message = Messages.get(status);
		
		if (status == Constants.SUCCESS) {
			if (message != null) {
				response.getSuccess().add(message);
			}
		}
		else if (status == Constants.WARNING) {
			message = Messages.get(warningStatus.intValue());
			if (message != null) {
				Warning warning = new Warning();
				warning.setMessage(message);
				warning.setType(warningStatus+"");
				warning.setComment(comment == null ? message : comment);
				response.getWarning().add(warning);
			}
		}
		else {
			
			Failure failure = new Failure();
			failure.setComment(comment == null ? message : comment);
			failure.setType(status+"");
			failure.setMessage(message);
			response.getFailure().add(failure);
		}
		
		return response;
	}

	@SuppressWarnings("serial")
	public static Response checkHotelAndCreds(String login, String password, String hotelCode, boolean isHotelRequired, UpdateInventoryRequest updateInventoryRequest) {
		
		if (login == null) 
			return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.REQUIRED_LOGIN, null, null);
        
		if (password == null) 
			return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.REQUIRED_PASSWORD, null, null);
		
		customerName = null;
		
		/* @INFO Checking the multi tenancy - where to add/update reservation by protel hotel code */
		NCIResponse nciResponse =  NCICustomerService.getCustomerByCredentials(login, password, Credentials.isDebugMode(), Credentials.getCommonDatabaseUrl(), Credentials.getCommonDatabaseUser(), Credentials.getCommonDatabaseDriver(), Credentials.getCommonDatabasePassword(), Credentials.getCommonDatabaseCatalog());
		String customerId = Credentials.getDefaultCustomerId();
		if (nciResponse.isSuccess()) {
			NCICustomerEntity nciCustomerEntity = (NCICustomerEntity) nciResponse.getData();
			customerId = nciCustomerEntity.getCustomerId();
			customerName = nciCustomerEntity.getName();
		}
		else 
    		return Utils.GetXmlFormat(Constants.BAD_AUTHENTICATION_3, null, hotelCode == null ? "Credentials is invalid for this Hotel" : "Credentials is invalid for this Hotel (" + hotelCode + ")", null, null);
        
		/* assign the customerId - multi tenancy applied */
		RequestContext.setCustomerId(customerId);

        if (customerName != null && updateInventoryRequest != null) {
        	String xmlString = "";
        	try {
	        	StringWriter sw = new StringWriter();
	        	JAXB.marshal(updateInventoryRequest, sw);
	        	xmlString = sw.toString();
        	}catch (Exception e) {}
        	
        	LoggingUtils.logging(null, xmlString, "Not able to save the response.", new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()), new ArrayList<String>() { { add(Constants.LOG_PATH.replace("#customer#", customerName)); } }, Constants.DEDGEREQUESTS_FILE);

        }
		if (isHotelRequired && hotelCode == null)
    		return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.REQUIRED_HOTELCODE, null, null);
		
		return null;
	}
	
	public static Date returnDateByFormat(String dateStr, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        Date date = null;
        try {
        	date = sdf.parse(dateStr);
        } catch (Exception e) {
        	if (Credentials.isDebugMode())
        		e.printStackTrace();
        }
        return date;
    }

	public static String returnDateFormatByDate(Date date, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        String dateStr = null;
        try {
        	dateStr = sdf.format(date);
        } catch (Exception e) {
        	if (Credentials.isDebugMode())
        		e.printStackTrace();
        }
        return dateStr;
    }

	public static Response isValidFromToDates(String fromStr, String toStr, String dateFromFormat, String dateToFormat, int duration) {
		
		/* return null = success */
		
		if (((fromStr == null || toStr == null) && duration == 0) ||
				((fromStr != null || toStr != null) && duration > 0)) 
			return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.REQUIRED_FROM_TO_DATES_OR_DURATION, null, null);

		if (duration != 0 && duration < 1) 
			return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.DURATION_VALUE_CHECK, null, null);
				
		Date from = returnDateByFormat(fromStr, Constants.DateTimeFormat);
		Date to = returnDateByFormat(toStr, Constants.DateTimeFormat);

		if ((from == null || to == null) && duration == 0) 
			return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.INVALID_DATE_FORMAT, null, null);
			
		if(from != null && to != null && to.before(from))
			return Utils.GetXmlFormat(Constants.INTERNAL_ERROR_203, null, Constants.INVALID_TO_FROM_DATE, null, null);
			
		return null;
	}

	public static Hotel validateHotel(HotelService hotelService, String dedgeHotelCode) {

		Hotel hotel = hotelService.findByDedgeHotelCode(dedgeHotelCode);
		if (hotel == null) return null;
		
		HotelServices hotelServices = hotelService.findHotelServiceByHotelId(hotel.getHotelId());
		if (hotelServices == null) return null;

		return hotel;
	}

	public static int validateStringNumber(String str) {
		try {
		    return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return -1;
	}


	public static int validateDuration(String durationStr, String fromStr, String toStr) {
		if (fromStr == null && toStr == null) {
			if (durationStr == null)
				return 1;
		} else {
			if (durationStr == null)
				return 0;
		}
		return validateStringNumber(durationStr);
	}

	public static Integer parseStringToInt(String strVal) {
        try {
            return Integer.parseInt(strVal);
        } catch (NumberFormatException ex){}
        return null;
	}
	
	/* Remove html tags, '&nbsp;', new line in the beginning and the end of the string  */
	public static String removeHtmlTags(String htmlString, boolean removeLineSeparator) {
		
		htmlString = htmlString.replaceAll("\\<.*?\\>", "").replace("&nbsp;", "").replaceAll("^[\n]*", "").replaceAll("[\n]*$", "");
	    return removeLineSeparator ? htmlString.replace(System.getProperty("line.separator"), "") :
	    	  htmlString;
	}

	/* Privacy policy saved as html in our database, so we should substring the policy from the html */
	public static String fetchPolicy(String policy, String policyType) {

		try {
			
			policy = removeHtmlTags(policy, true);
	
		    String guaranteePolicy = "Guarantee Policy:";
		    String cancellationPolicy = "Cancellation Policy:";
			int fromChar = policy.indexOf(guaranteePolicy);
			int toChar = policy.indexOf(cancellationPolicy);
	 
			if (policyType.equals("guarantee")) 
				return policy.substring(fromChar + guaranteePolicy.length(), toChar).replaceAll("^[\n]*", "").replaceAll("[\n]*$", "");
			
			else if (policyType.equals("cancel")) 
				return policy.substring(toChar + cancellationPolicy.length(), policy.length()).replaceAll("^[\n]*", "").replaceAll("[\n]*$", "");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return policy;
	}
	
	public static long calculateTwoDateDifference (Date date1, Date date2) {
		
		if (date1.equals(date2))
			return 1;
		
		Date from = date1.before(date2) ? date1 : date2;
		Date to = date1.before(date2) ? date2 : date1;
		
	    long diffInMillies = Math.abs(from.getTime() - to.getTime());
	    long dateDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    
	    return dateDiff;
	}
	
	public static String fixImagePath(String currentImgPath) {
		return currentImgPath.replace(Constants.TSL_MIDDATA_PATH, "");
	}
	

	public static String makeBlankIfEmpty(String text) {
		if (text == null || text.equalsIgnoreCase("null") || text.isEmpty()) 
			text = "";
		return text;
	}
}
