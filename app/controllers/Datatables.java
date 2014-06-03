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
import java.util.*;

public class Datatables extends Controller {

    public static Result options() {
        String id = request().getQueryString("id");
        String title = request().getQueryString("title");
        String model = request().getQueryString("model");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        ObjectNode options = null;
        if("prf_subsystem".equals(model))
            options = getSubsystemPrf(id,title,start_time,end_time);
        else if("prf_raidgroup".equals(model))
            options = getRaidGroupPrf(id,title,start_time,end_time);
        else if("prf_fcport".equals(model))
            options = getFCPortPrf(id,title,start_time,end_time);
        else if("cfg_fcport".equals(model))
            options = getFCPortCfg(id,title);
        return ok(options);
    }

    private static ObjectNode getSubsystemPrf(String id, String title, String start_time, String end_time) {
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

    private static ObjectNode getFCPortPrf(String id, String title, String start_time, String end_time) {
        String[] kpiColumns = {"IOPS","Transfer(mb)"};
        String chpNum = "ABCDEFGHIJKLMNOPQ";
        Random random = new Random();
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        cols.add("名称");
        for (String colname : kpiColumns)
            cols.add(colname);
        for (int i=1;i < 12;i++) {
            for (int j = 0; j < chpNum.length(); j++) {
                String name = "CL" + i + "-" + chpNum.charAt(j);
                ArrayNode obj = rows.addArray();
                obj.add(name);
                for (String colname : kpiColumns)
                    obj.add(random.nextInt(130));
            }
        }
        return options;
    }

    private static ObjectNode getFCPortCfg(String id, String title) {
        String[] kpiColumns = {"Name","WWN","Type","Speed","Role","Number of LDEVs"};
        String chpNum = "ABCDE";
        Random random = new Random();
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        for (String colname : kpiColumns)
            cols.add(colname);
        for (int i=1;i < 10;i++) {
            for (int j = 0; j < chpNum.length(); j++) {
                String name = "CL" + i + "-" + chpNum.charAt(j);
                ArrayNode obj = rows.addArray();
                obj.add(name);
                obj.add("50:06:0E:80:05:74:96:"+i+chpNum.charAt(j));
                obj.add("FICON");
                obj.add("Unknown");
                obj.add("Initiator");
                obj.add(random.nextInt(30));
            }
        }
        return options;
    }

    private static ObjectNode getRaidGroupPrf(String id, String title, String start_time, String end_time) {
        String[] kpiColumns = {"IOPS","Transfer(mb)","Response Time(ms)","Cache Hits(%)"};
        Random random = new Random();
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        cols.add("名称");
        cols.add("类型");
        cols.add("Raid Level");
        for (String colname : kpiColumns)
            cols.add(colname);
        for (int i=1;i < 12;i++) {
            for (int j = 1; j < 12; j++) {
                String name = "1-" + i + "-" + j;
                ArrayNode obj = rows.addArray();
                obj.add(name);
                obj.add("FC");
                obj.add("RAID5(3D+1P)");
                for (String colname : kpiColumns)
                    obj.add(random.nextInt(130));
            }
        }
        return options;
    }

}
