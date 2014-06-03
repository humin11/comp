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

public class Charts extends Controller {

    public static Result options() {
        String id = request().getQueryString("id");
        String title = request().getQueryString("title");
        String kpi = request().getQueryString("kpi");
        String sub_kpi = request().getQueryString("sub_kpi");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        ObjectNode options = null;
        if("capacity".equalsIgnoreCase(sub_kpi)){
            if("".equals(id))
                options = column(id,title,kpi,sub_kpi);
            else
                options = pie(id,title,kpi,sub_kpi);
        }else if("subsystem_topn".equalsIgnoreCase(kpi) || "subsystem_raidgroup_topn".equalsIgnoreCase(kpi)){
            options = bar(id,title,kpi,sub_kpi);
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
        options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        options.putObject("legend").put("maxHeight",40);
        ObjectNode xAxis = options.putObject("xAxis");
        xAxis.put("type", "datetime");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        options.putObject("plotOptions").putObject("spline").putObject("marker").put("enabled",false);
        ArrayNode series = options.putArray("series");
        if("subsystem".equalsIgnoreCase(kpi))
            getSubsystemPrf(id, sub_kpi, start_time, end_time, series);
        else if("raidgroup".equalsIgnoreCase(kpi))
            getRaidGroupPrf(id, sub_kpi, start_time, end_time, series);
        return options;
    }

    public static ObjectNode column(String id, String title, String kpi, String sub_kpi) {
        String unit = "TB";
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("animation", false);
        chart.put("backgroundColor", "#fff");
        chart.put("type","column");
        ObjectNode options3d = chart.putObject("options3d");
        options3d.put("enabled",false);
        options3d.put("alpha",15);
        options3d.put("beta",15);
        options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        ObjectNode xAxis = options.putObject("xAxis");
        ArrayNode categories = xAxis.putArray("categories");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        ObjectNode stackLabels = yAxis.putObject("stackLabels");
        stackLabels.put("enabled", true);
        stackLabels.put("format", "{total} "+unit);
        stackLabels.putObject("style").put("textShadow","0 0 3px black");
        ObjectNode column = options.putObject("plotOptions").putObject("column");
        column.put("stacking","normal");
        column.put("depth",40);
        ObjectNode dataLabels = column.putObject("dataLabels");
        dataLabels.put("enabled",true);
        dataLabels.put("color","white");
        dataLabels.putObject("style").put("textShadow","0 0 3px black, 0 0 3px black");
        ArrayNode series = options.putArray("series");
        getSubsystemCapacity(id, categories, series);
        return options;
    }

    public static ObjectNode bar(String id, String title, String kpi, String sub_kpi) {
        String unit = "%";
        if("response".equalsIgnoreCase(sub_kpi))
            unit = "s";
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("animation", false);
        chart.put("backgroundColor", "#fff");
        chart.put("type","bar");
        options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        options.putObject("legend").put("enabled", false);
        ObjectNode xAxis = options.putObject("xAxis");
        ArrayNode categories = xAxis.putArray("categories");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        ObjectNode stackLabels = yAxis.putObject("stackLabels");
        stackLabels.put("enabled", true);
        stackLabels.put("format", "{total} "+unit);
        stackLabels.putObject("style").put("textShadow","0 0 3px black");
        ObjectNode column = options.putObject("plotOptions").putObject("bar");
        column.put("stacking","normal");
        ObjectNode dataLabels = column.putObject("dataLabels");
        dataLabels.put("enabled",false);
        dataLabels.put("color","white");
        dataLabels.putObject("style").put("textShadow","0 0 3px black");
        ArrayNode series = options.putArray("series");
        getRaidGroupPrfTopn(id, sub_kpi, categories, series);
        return options;
    }

    public static ObjectNode pie(String id, String title, String kpi, String sub_kpi) {
        String unit = "TB";
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("animation", false);
        chart.put("backgroundColor", "#fff");
        chart.put("type","pie");
        ObjectNode options3d = chart.putObject("options3d");
        options3d.put("enabled",false);
        options3d.put("alpha",45);
        options3d.put("beta",0);
        ObjectNode titleNode = options.putObject("title");
        titleNode.putObject("style").put("fontSize","14px");
        titleNode.put("align", "center");
        titleNode.put("verticalAlign", "middle");
        titleNode.put("y", -20);
        options.putObject("credits").put("enabled", false);
        options.putObject("legend").put("labelFormat","{name}: {y} "+unit);
        ObjectNode pie = options.putObject("plotOptions").putObject("pie");
        pie.put("depth",35);
        ObjectNode dataLabels = pie.putObject("dataLabels");
        dataLabels.put("enabled",true);
        dataLabels.put("color","white");
        dataLabels.put("distance",-10);
        pie.put("showInLegend",true);
        dataLabels.put("format","{point.percentage:.0f}%");
        dataLabels.putObject("style").put("textShadow","0 0 3px black, 0 0 3px black");
        ArrayNode series = options.putArray("series");
        getSubsystemCapacity(id, series,titleNode);
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

    private static void getSubsystemCapacity(String id, ArrayNode series,ObjectNode titleNode) {
        Random random = new Random();
        ObjectNode capacity = series.addObject();
        capacity.put("name", "容量");
        capacity.put("type", "pie");
        capacity.put("innerSize","80%");
        ArrayNode data = capacity.putArray("data");
        ObjectNode used = data.addObject();
        used.put("name","已使用");
        used.put("color", "#73B0E2");
        int usedValue = random.nextInt(1000);
        used.put("y",usedValue);
        ObjectNode unused = data.addObject();
        unused.put("name","未使用");
        unused.put("color", "#3CB371");
        int unusedValue = random.nextInt(1000);
        unused.put("y",unusedValue);
        titleNode.put("text",(usedValue+unusedValue)+" TB");
    }

    private static void getSubsystemPrf(String id, String sub_kpi, String start_time, String end_time, ArrayNode series) {
        long testStartTime = 1400480760000L;
        long interval = 60000;
        int count = 50;
        Random random = new Random();
        String[] testSubsystems = {"USPV.29846", "USPV.29416", "VSP.90873"};
        for (String subsystem : testSubsystems) {
            if("".equals(id) || subsystem.equals(id)){
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

    private static void getRaidGroupPrf(String id, String sub_kpi, String start_time, String end_time, ArrayNode series) {
        long testStartTime = 1400480760000L;
        long interval = 60000;
        int count = 60;
        Random random = new Random();
        for (int i=1;i < 5;i++) {
            for (int j=1;j<5;j++){
                String name = "1-"+i+"-"+j;
                ObjectNode serie = series.addObject();
                serie.put("id", name);
                serie.put("name", name);
                ArrayNode data = serie.putArray("data");
                for (int k = 0; k < count; k++){
                    ObjectNode xy = data.addObject();
                    xy.put("x",testStartTime +  k * interval);
                    xy.put("y",random.nextInt(130));
                }
            }
        }
    }

    private static void getRaidGroupPrfTopn(String id,String sub_kpi, ArrayNode categories, ArrayNode series) {
        Random random = new Random();
        ArrayList<String[]> sortList = new ArrayList<String[]>();
        for (int i=1;i < 5;i++) {
            for (int j = 1; j < 12; j++) {
                String name = "1-" + i + "-" + j;
                int curr = random.nextInt(100);
                sortList.add(new String[]{name,curr+""});
            }
        }
        Collections.sort(sortList, new Comparator<String[]>() {
            public int compare(String[] strings, String[] strings2) {
                int str1 = Integer.parseInt(strings[1]);
                int str2 = Integer.parseInt(strings2[1]);
                if (str1 > str2)
                    return -1;
                else if (str1 < str2)
                    return 1;
                else
                    return 0;
            }
        });
        if(!sortList.isEmpty()) {
            ObjectNode serie = series.addObject();
            serie.put("id", sub_kpi);
            serie.put("name", sub_kpi);
            ArrayNode data = serie.putArray("data");
            for (int i = 0; i < 10 && i < sortList.size(); i++) {
                String[] sorted = sortList.get(i);
                categories.add(sorted[0]);
                data.add(Integer.parseInt(sorted[1]));
            }
        }
    }



}
