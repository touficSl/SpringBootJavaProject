package com.tsspringexperience.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLog {
	
	private static String catalog;

	public static void print(String args, boolean printLog) {
		
		if (!printLog)
			return;
		
		String ServerSignature = "[NCI]";
		String DatabaseConnected = "-- " + catalog + " --";
		String ServerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		String tab = "\t";
		
		System.out.println(ServerDate + tab + ServerSignature + tab + DatabaseConnected + tab + args);
	}

	public static void printError(String args) {
		
		String ServerSignature = "[NCI Error]";
		String DatabaseConnected = "-- " + catalog + " --";
		String ServerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		String tab = "\t";
		
		System.out.println(ServerDate + tab + ServerSignature + tab + DatabaseConnected + tab + args);
	}

	public static String getCatalog() {
		return catalog;
	}

	public static void setCatalog(String catalog) {
		ConsoleLog.catalog = catalog;
	}
	
}
