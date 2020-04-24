package dev.niro.valorantcheat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public static void log(Object text) {
		System.out.println(getCurrentTimeStamp() + text);
	}
	
	public static void err(Object text) {
		System.err.println(getCurrentTimeStamp() + text);
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("[yyyy-MM-dd_HH:mm:ss] ");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
}
