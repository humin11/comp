package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Configuration;
import play.libs.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Highcharts {

    public static String export(ObjectNode chart){
        String lines = "";
        try {
            String phantomjs = Configuration.root().getString("phantomjs.url","http://127.0.0.1:3003/");
            URL url = new URL(phantomjs);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(500);
            conn.setReadTimeout(6000);
            conn.setRequestProperty("Content-Type","application/json");
            ObjectNode json = Json.newObject();
            ObjectNode infile = json.putObject("infile");
            infile.put("type","json");
            ObjectNode options = json.putObject("options");
            options.setAll(chart);
            json.put("type","png");
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("utf-8"));
            os.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null){
                lines += line.trim();
            }
            reader.close();
            conn.disconnect();
            return lines;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
