package utils;

import java.text.DecimalFormat;
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

    public static String splitWWN(String wwn){
        if(wwn == null)
            return "";
        char[] tmp = wwn.toCharArray();
        String result = "";
        for(int i=0;i<tmp.length;i++){
            if(i>0 && i%2==0)
                result+=":";
            result+=tmp[i];
        }
        return result;
    }

	public static String parserCapacity(long kb){
	  if(kb*1.0<=0)
		  kb = 1;
	  String[]  unitArr = {"KB","MB","GB","TB","PB","EB","ZB","YB"};
	  Double index= 0d;
	  index=Math.floor(Math.log(kb)/Math.log(1024));

	  DecimalFormat df = new DecimalFormat("####.00");
	  double size =kb/Math.pow(1024,index.doubleValue());
	  return df.format(size)+" "+unitArr[index.intValue()];
	}

    public static Double parserCapacityGB(long kb){;
	  DecimalFormat df = new DecimalFormat("####.00");
	  return Double.parseDouble(df.format(kb/1024/1024));
	}

    public static Double parserCapacityTB(long kb){;
	  DecimalFormat df = new DecimalFormat("####.00");
	  return Double.parseDouble(df.format(kb/1024/1024/1024));
	}

    public static String[] parserCapacitySplit(long kb){
        if(kb*1.0<=0)
            kb = 1;
        String[]  unitArr = {"KB","MB","GB","TB","PB","EB","ZB","YB"};
        Double index= 0d;
        index=Math.floor(Math.log(kb)/Math.log(1024));

        DecimalFormat df = new DecimalFormat("####.00");
        double size =kb/Math.pow(1024,index.doubleValue());
        return new String[]{df.format(size)+"",unitArr[index.intValue()]};
    }


    public static String splitElementIds(String element_id){
        String[] ids = element_id.split(",");
        String result = "(";
        for(String id : ids)
            result += ("'"+id+"',");
        result = result.substring(0,result.length()-1);
        result += ")";
        return result;
    }

}
