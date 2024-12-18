package com.tsspringexperience.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

public class HtmlEmailUtils {

	// private static String serverImageServer =
	// Configuration.getCheckinEmailsImageServer();
	
	public static String emailContentWithHtml(String resource,
			Map<String, String> fieldsAndValues) {
		return emailContentWithHtml(resource, fieldsAndValues, false);
	}

	public static String emailContentWithHtml(String resource,
			Map<String, String> fieldsAndValues, boolean isLocalResource) {
		// resource exemple serverImageServer+"/complete_checkin.html"
		String formContent = "";
		StringBuilder builder = new StringBuilder();
		
		InputStream stream = null;
		if(isLocalResource){
			stream =  HtmlEmailUtils.class
					.getResourceAsStream(resource);
		}else{
			try {
				stream = new FileInputStream(resource);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				stream, Charset.forName("UTF-8")))) {
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				builder.append(sCurrentLine);
			}
			formContent = builder.toString();
			if (fieldsAndValues != null) {
				for (Map.Entry<String, String> entry : fieldsAndValues
						.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					formContent = formContent.replace(key,
							Utils.makeBlankIfEmpty(value));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return formContent;
	}
}
