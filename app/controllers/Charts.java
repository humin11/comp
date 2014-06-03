package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;
import utils.Format;
import views.html.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;

public class Charts extends Controller {

    public static Result options() {
        String id = request().getQueryString("id");
        String title = request().getQueryString("title");
        String type = request().getQueryString("type");
        String kpi = request().getQueryString("kpi");
        String sub_kpi = request().getQueryString("sub_kpi");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        ObjectNode options = null;
        if("subsystem_cap".equals(kpi)){
            options = column(id,title,kpi,sub_kpi);
        }else{
            options = line(id,title,kpi,sub_kpi,start_time,end_time);
        }
        return ok(options);
    }

    public static ObjectNode line(String id, String title, String kpi, String sub_kpi, String start_time, String end_time) {
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("zoomType", "x");
        chart.put("animation", false);
        chart.put("backgroundColor", "#fff");
        chart.put("type","spline");
        if(!"".equals(title))
            options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        ObjectNode xAxis = options.putObject("xAxis");
        xAxis.put("type", "datetime");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        options.putObject("plotOptions").putObject("spline").putObject("marker").put("enabled",false);
        ArrayNode series = options.putArray("series");
        getSubsystemPrf(id, sub_kpi, start_time, end_time, series);
        return options;
    }

    public static ObjectNode column(String id, String title, String kpi, String sub_kpi) {
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("zoomType", "x");
        chart.put("animation", false);
        chart.put("backgroundColor", "#fff");
        chart.put("type","column");
        if(!"".equals(title))
            options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        ObjectNode xAxis = options.putObject("xAxis");
        ArrayNode categories = xAxis.putArray("categories");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        yAxis.putObject("stackLabels").put("enabled", true);
        ObjectNode column = options.putObject("plotOptions").putObject("column");
        column.put("stacking","normal");
        ObjectNode dataLabels = column.putObject("dataLabels");
        dataLabels.put("enabled",true);
        dataLabels.put("color","white");
        dataLabels.putObject("style").put("textShadow","0 0 3px black, 0 0 3px black");
        ArrayNode series = options.putArray("series");
        getSubsystemCapacity(id, categories, series);
        return options;
    }

    private static void getSubsystemCapacity(String id, ArrayNode categories, ArrayNode series) {
        Random random = new Random();
        String[] testSubsystems = {"USPV.29846", "USPV.29416", "VSP.90873"};
        ObjectNode unused = series.addObject();
        unused.put("id", "未使用");
        unused.put("name", "未使用");
        unused.put("color", "#3CB371");
        ArrayNode unusedData = unused.putArray("data");
        ObjectNode used = series.addObject();
        used.put("id", "已使用");
        used.put("name", "已使用");
        used.put("color", "#73B0E2");
        ArrayNode usedData = used.putArray("data");
        for (String subsystem : testSubsystems) {
            categories.add(subsystem);
            unusedData.add(random.nextInt(10000));
            usedData.add(random.nextInt(10000));
        }
    }

    private static void getSubsystemPrf(String id, String sub_kpi, String start_time, String end_time, ArrayNode series) {
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

}
