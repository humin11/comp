package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;

public class Charts extends Controller {

    public static Result show() {
        String uid = UUID.randomUUID().toString();
        String id = request().getQueryString("id");
        String type = request().getQueryString("type");
        String title = request().getQueryString("title");
        String kpi = request().getQueryString("kpi");
        String sub_kpi = request().getQueryString("sub_kpi");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        String refresh = request().getQueryString("refresh");
        ObjectNode options = line(id,title,kpi,sub_kpi,start_time,end_time);
        String jsonString = "{}";
        try {
            jsonString = URLEncoder.encode(options.toString().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        };
        if("line".equalsIgnoreCase(type))
            return ok(line.render(uid, jsonString));
        else
            return ok();
    }

    public static Result options() {
        String id = request().getQueryString("id");
        String title = request().getQueryString("title");
        String type = request().getQueryString("type");
        String kpi = request().getQueryString("kpi");
        String sub_kpi = request().getQueryString("sub_kpi");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        ObjectNode options = line(id,title,kpi,sub_kpi,start_time,end_time);
        return ok(options);
    }

    private static ObjectNode line(String id, String title, String kpi, String sub_kpi, String start_time, String end_time) {
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("zoomType", "x");
        chart.put("animation", false);
        chart.put("backgroundColor","#fff");
        chart.put("type","spline");
        if(!"".equals(title))
            options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        ObjectNode xAxis = options.putObject("xAxis");
        xAxis.put("type", "datetime");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        ArrayNode series = options.putArray("series");
        getSubsystemSummary(id, sub_kpi, start_time, end_time, series);
        return options;
    }

    private static void getSubsystemSummary(String id, String sub_kpi, String start_time, String end_time, ArrayNode series) {
        long testStartTime = 1400480760000L;
        long interval = 60000;
        int count = 50;
        Random random = new Random();
        String[] testSubsystems = {"USPV.29846", "USPV.29416", "VSP.90873"};
        for (String subsystem : testSubsystems) {
            ObjectNode serie = series.addObject();
            serie.put("id", subsystem);
            serie.put("name", subsystem);
            ArrayNode data = serie.putArray("data");
            for (int i = 0; i < count; i++){
                ObjectNode xy = data.addObject();
                xy.put("x",testStartTime +  i * interval);
                xy.put("y",random.nextInt(130));
            }
        }
    }

    public static Result export() {

        return ok();
    }

}
