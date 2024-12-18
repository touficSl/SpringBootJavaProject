package com.tsspringexperience.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoggingUtils {
	
	 public static void logging (String log, String request, String response, String inSec, List<String> filePaths, String fileName) {

		try {
			
			if (!Credentials.saveFileSystemLogs())
				return;
				
			for (String filePath : filePaths) {
				String out = new SimpleDateFormat("'" + fileName + "-'" + "yyyy-MM-dd'.txt'").format(new Date());
				String outSec = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date());
				
				if (log != null) 
					FileUtils.writeIntoFile(filePath+out , outSec + "-- " + log);
				else {
					FileUtils.writeIntoFile(filePath+out , "--Request  -----------------------------------" + inSec + "--------------------------------------");
					FileUtils.writeIntoFile(filePath+out , request);
					FileUtils.writeIntoFile(filePath+out , "--Response -----------------------------------" + outSec+ "--------------------------------------");
					FileUtils.writeIntoFile(filePath+out , response);
					FileUtils.writeIntoFile(filePath+out , "------------------------------------------------------------------------------------------------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}