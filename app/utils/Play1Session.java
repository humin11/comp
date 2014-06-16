package utils;

import play.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Play1Session {


    public static String secret = "V7DIoo1DIAPsaTG8BFMJBSBwYRQFbvW1781YpGDtRQDrYExTdX3OMJp0XudEdclg";

    public static String params = "%00username%3AAadmin%00%00GroupName%3AAdmin%00%00___AT%3Ab6693bab5e8a57e360834cac6e538867e7464998%00%00UserName%3AAadmin%00";

    static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String sign(String username){
        return sign(username, secret.getBytes());
    }

    public static String getSessionName(){
        return "PLAY_SESSION";
    }


    public static String getSessionParams(){
        return sign(params)+"-"+ params;
    }

    public static String sign(String message, byte[] key) {
        if (key.length == 0) {
            return message;
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            mac.init(signingKey);
            byte[] messageBytes = message.getBytes("utf-8");
            byte[] result = mac.doFinal(messageBytes);
            int len = result.length;
            char[] hexChars = new char[len * 2];


            for (int charIndex = 0, startIndex = 0; charIndex < hexChars.length;) {
                int bite = result[startIndex++] & 0xff;
                hexChars[charIndex++] = HEX_CHARS[bite >> 4];
                hexChars[charIndex++] = HEX_CHARS[bite & 0xf];
            }
            return new String(hexChars);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
