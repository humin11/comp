package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    public static Date parseDate(String in, String pas) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pas);
		try {
			d = sdf.parse(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

    public static String parseString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

    public static String parseDateString(long timestamp,String pattern){
		String pas = "";
		Date d = new Date(timestamp);
		pas = parseString(d,pattern);
		return pas;
	}

}
