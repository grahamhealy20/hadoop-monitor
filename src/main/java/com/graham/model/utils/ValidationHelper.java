package com.graham.model.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
	private static String ipRegex = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
	private static String numberRegex = "[0-9]*";
	
	
	//Validates a valid IP address
	public static boolean validateIpAddress(String ipAddress) {
		if(ipAddress != null) {
			Pattern p = Pattern.compile(ipRegex);
			Matcher m = p.matcher(ipAddress);
			return m.matches();
		} else {
			return false;
		}
	}
	
	public static boolean isNumeric(String text) {
		Pattern p = Pattern.compile(numberRegex);
		Matcher m = p.matcher(text);
		return m.matches();
	}
	
	public static boolean required(String text) {
		if(text != null) {
			if(text.length() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
}
