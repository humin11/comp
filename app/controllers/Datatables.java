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

public class Datatables extends Controller {

    public static Result options() {
        String id = request().getQueryString("id");
        String title = request().getQueryString("title");
        String model = request().getQueryString("model");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        ObjectNode options = null;
        if("subsystem".equals(model))
            options = getSubsystemSummary(id,title,model,start_time,end_time);
        return ok(options);
    }

    private static ObjectNode getSubsystemSummary(String id, String title, String model, String start_time, String end_time) {

        long testStartTime = 1400480760000L;
        long interval = 60000;
        int count = 50;
        String[] testSubsystems = {"USPV.29846", "USPV.29416", "VSP.90873"};
        String[] kpiColumns = {"IOPS","Transfer(mb)","Response Time(ms)","Cache Hits(%)"};
        Random random = new Random();

        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        cols.add("名称");
        cols.add("时间");
        for (String colname : kpiColumns)
            cols.add(colname);

        for (int i = 0; i < count; i++){
            ArrayNode obj = rows.addArray();
            obj.add(testSubsystems[random.nextInt(2)]);
            obj.add(Format.parseDateString(testStartTime + random.nextInt(50)*interval,"yyyy-MM-dd HH:mm:ss"));
            for (String colname : kpiColumns)
                obj.add(random.nextInt(130));
        }
        return options;
    }

}