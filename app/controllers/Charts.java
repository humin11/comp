package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.TResStorageSubsystem;
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
        String math = request().getQueryString("math");
        String start_time = request().getQueryString("start_time");
        String end_time = request().getQueryString("end_time");
        ObjectNode options = null;
        if("capacity".equalsIgnoreCase(sub_kpi)){
            if("".equals(id))
                options = column(id,title,kpi,sub_kpi);
            else
                options = pie(id,title,kpi,sub_kpi);
        }else if("subsystem_topn".equalsIgnoreCase(kpi)){
            options = bar(id,title,kpi,sub_kpi,math,start_time,end_time);
        }else{
            options = line(id,title,kpi,sub_kpi,math,start_time,end_time);
        }
        return ok(options);
    }

    public static ObjectNode line(String id, String title, String kpi, String sub_kpi,String math, String start_time, String end_time) {
        ObjectNode options = Json.newObject();
        ObjectNode chart = options.putObject("chart");
        chart.put("zoomType", "x");
        chart.put("animation", false);
        chart.put("backgroundColor", "#fff");
        chart.put("type","spline");
        options.putObject("title").put("text", title);
        options.putObject("credits").put("enabled", false);
        options.putObject("legend").put("maxHeight",80);
        ObjectNode xAxis = options.putObject("xAxis");
        xAxis.put("type", "datetime");
        ObjectNode yAxis = options.putObject("yAxis");
        yAxis.putObject("title").put("text", "");
        yAxis.put("min",0);
        options.putObject("plotOptions").putObject("spline").putObject("marker").put("enabled",false);
        ArrayNode series = options.putArray("series");
        if("subsystem".equalsIgnoreCase(kpi))
            getSubsystemPrf(id, sub_kpi,math, start_time, end_time, series);
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

    public static ObjectNode bar(String id, String title, String kpi, String sub_kpi,String math,String start_time, String end_time) {
        String unit = "%";
        if("pg_response".equalsIgnoreCase(sub_kpi))
            unit = "s";
        if("pg_transfer".equalsIgnoreCase(sub_kpi))
            unit = "mb";
        if("pg_io".equalsIgnoreCase(sub_kpi))
            unit = "";
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
        if("subsystem_topn".equalsIgnoreCase(kpi)){
            getSubsystemPrfTopn(id, sub_kpi,math,start_time,end_time, categories, series);
        }
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
        List<TResStorageSubsystem> subsystems = TResStorageSubsystem.findAll();
        for (TResStorageSubsystem subsystem : subsystems) {
            categories.add(subsystem.NAME.length()>15?subsystem.NAME.substring(0,15)+"...":subsystem.NAME);
            unusedData.add(Format.parserCapacityTB(subsystem.ALLOCATED_CAPACITY));
            usedData.add(Format.parserCapacityTB(subsystem.ASSIGNED_CAPACITY-subsystem.ALLOCATED_CAPACITY));
        }
    }

    private static void getSubsystemCapacity(String id, ArrayNode series,ObjectNode titleNode) {
        TResStorageSubsystem subsystem = TResStorageSubsystem.findById(id);
        ObjectNode capacity = series.addObject();
        capacity.put("name", "容量");
        capacity.put("type", "pie");
        capacity.put("innerSize","80%");
        ArrayNode data = capacity.putArray("data");
        ObjectNode used = data.addObject();
        used.put("name","已使用");
        used.put("color", "#73B0E2");
        double usedValue = Format.parserCapacityTB(subsystem.ALLOCATED_CAPACITY);
        used.put("y",usedValue);
        ObjectNode unused = data.addObject();
        unused.put("name","未使用");
        unused.put("color", "#3CB371");
        double unusedValue = Format.parserCapacityTB(subsystem.ASSIGNED_CAPACITY - subsystem.ALLOCATED_CAPACITY);
        unused.put("y",unusedValue);
        titleNode.put("text",(usedValue+unusedValue)+" TB");
    }

    private static void getSubsystemPrf(String id, String sub_kpi,String math, String start_time, String end_time, ArrayNode series) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -2);
        String startTime = c.getTimeInMillis()+"";
        String endTime = "";
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyyMMddHHmm").getTime()+"";
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyyMMddHHmm").getTime()+"";
        }
        String timescope = " and c.DEV_TIME>"+startTime;
        if(!"".equals(endTime))
            timescope += (" and c.DEV_TIME<"+endTime);
        String idscope = "";
        String ids = "";
        if(!"".equals(id)) {
            ids = Format.splitElementIds(id);
            idscope = " and a.ID in " + ids;
        }
        HashMap<String,ObjectNode> serieMap = new HashMap<String,ObjectNode>();
        List<TResStorageSubsystem> subsystems = TResStorageSubsystem.findAll();
        for (TResStorageSubsystem subsystem : subsystems) {
            if ("".equals(id) || subsystem.ID.equals(id)) {
                ObjectNode serie = series.addObject();
                serie.put("id", subsystem.ID);
                serie.put("name", subsystem.NAME.length()>15?subsystem.NAME.substring(0,15)+"...":subsystem.NAME);
                serie.putArray("data");
                serieMap.put(subsystem.ID, serie);
            }
        }
        String sql = "";
        String column = "";
        String subWhere = " ";
        if("pg_usage".equalsIgnoreCase(sub_kpi) || "pg_io".equalsIgnoreCase(sub_kpi) || "pg_response".equalsIgnoreCase(sub_kpi)
               || "pg_transfer".equalsIgnoreCase(sub_kpi) || "pg_read_hits".equalsIgnoreCase(sub_kpi) || "pg_write_hits".equalsIgnoreCase(sub_kpi)){
            if("pg_usage".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.UTILIZATION),1)";
                subWhere = " and c.UTILIZATION>=0 ";
            } else if("pg_io".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.TOTAL_IO),1)";
                subWhere = " and c.TOTAL_IO>=0 ";
            } else if("pg_response".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.TOTAL_TIME),1)";
                subWhere = " and c.TOTAL_TIME>=0 ";
            } else if("pg_transfer".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.TOTAL_KB),1)";
                subWhere = " and c.TOTAL_KB>=0 ";
            } else if("pg_read_hits".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.READ_HITS),1)";
                subWhere = " and c.READ_HITS>=0 ";
            } else if("pg_write_hits".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.WRITE_HITS),1)";
                subWhere = " and c.WRITE_HITS>=0 ";
            }
            sql = "select c.DEV_TIME,a.ID,"+column+" as VAL " +
                "from T_Res_Storage_Subsystem a,T_Res_RaidGroup b,T_Prf_Dsraidgroup c "+
                "where a.ID=b.SUBSYSTEM_ID and b.ID=c.ELEMENT_ID " + subWhere +
                timescope +
                idscope +
                " group by c.DEV_TIME,a.ID " +
                "order by c.DEV_TIME asc";
        }else if("port_io".equalsIgnoreCase(sub_kpi)){
            sql = "select c.DEV_TIME,a.ID,MAX(c.TOTAL_IO) as VAL " +
                "from T_Res_Storage_Subsystem a,T_Res_Port b,T_Prf_Dsport c "+
                "where a.ID=b.SUBSYSTEM_ID and b.ID=c.ELEMENT_ID " +
                timescope +
                idscope +
                " group by c.DEV_TIME,a.ID " +
                "order by c.DEV_TIME asc";
        }else if("port_response".equalsIgnoreCase(sub_kpi)){
            sql = "select c.DEV_TIME,a.ID,MAX(c.TOTAL_TIME) as VAL " +
                "from TResStorageSubsystem a,T_Res_Port b,T_Prf_Dsport c "+
                "where a.ID=b.SUBSYSTEM_ID and b.ID=c.ELEMENT_ID and c.TOTAL_TIME>=0 " +
                timescope +
                idscope +
                " group by c.DEV_TIME,a.ID " +
                "order by c.DEV_TIME asc";
        }else if("chp_usage".equalsIgnoreCase(sub_kpi)||"dkp_usage".equalsIgnoreCase(sub_kpi)){
            if("chp_usage".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.UTILIZATION),1)";
                subWhere = " and c.UTILIZATION>=0 and c.ELEMENT_TYPE='CHP' ";
            } else if("dkp_usage".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.UTILIZATION),1)";
                subWhere = " and c.UTILIZATION>=0 and c.ELEMENT_TYPE='DKP' ";
            }
            sql = "select c.DEV_TIME,a.ID,"+column+" as VAL " +
                "from T_Res_Storage_Subsystem a,T_Prf_Controller c "+
                "where a.ID=c.SUBSYSTEM_ID " +subWhere+
                timescope +
                idscope +
                " group by c.DEV_TIME,a.ID " +
                "order by c.DEV_TIME asc";
        }else if("cache_write_pending".equalsIgnoreCase(sub_kpi)){
            sql = "select c.DEV_TIME,a.ID,ROUND(AVG(c.WRITE_PENDING),1) as VAL " +
                "from T_Res_Storage_Subsystem a,T_Prf_Cache c "+
                "where a.ID=c.SUBSYSTEM_ID and c.WRITE_PENDING >= 0 " +
                timescope +
                idscope +
                " group by c.DEV_TIME,a.ID " +
                "order by c.DEV_TIME asc";
        }
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> results = sqlQuery.findList();
        for(SqlRow row : results){
            ObjectNode serie = serieMap.get(row.getString("ID"));
            ArrayNode data = (ArrayNode)serie.findValue("data");
            ObjectNode xy = data.addObject();
            xy.put("x",row.getLong("DEV_TIME"));
            xy.put("y",row.getDouble("VAL"));
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

    private static void getSubsystemPrfTopn(String id,String sub_kpi,String math, String start_time, String end_time,ArrayNode categories, ArrayNode series) {
        int limit = 10;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -5);
        String startTime = c.getTimeInMillis()+"";
        String endTime = "";
        String startTimeDisplay = Format.parseString(c.getTime(),"yyyy-MM-dd HH:mm");
        String endTimeDisplay = "now";
        if(!start_time.equals("")){
            startTimeDisplay = Format.parseString(Format.parseDate(start_time,"yyyyMMddHHmm"),"yyyy-MM-dd HH:mm");
            startTime = Format.parseDate(start_time,"yyyyMMddHHmm").getTime()+"";
        }
        if(!end_time.equals("")) {
            endTimeDisplay = Format.parseString(Format.parseDate(end_time,"yyyyMMddHHmm"),"yyyy-MM-dd HH:mm");
            endTime = Format.parseDate(end_time, "yyyyMMddHHmm").getTime()+"";
        }
        String timescope = "and c.DEV_TIME>"+startTime;
        if(!"".equals(endTime))
            timescope += (" and c.DEV_TIME<"+endTime);
        String idscope = "";
        String ids = "";
        if(!"".equals(id)) {
            ids = Format.splitElementIds(id);
            idscope = " and a.ID in " + ids;
        }
        String sql = "";
        String column = "";
        String subWhere = " ";
        if("pg_usage".equalsIgnoreCase(sub_kpi) || "pg_io".equalsIgnoreCase(sub_kpi) || "pg_response".equalsIgnoreCase(sub_kpi)
           || "pg_transfer".equalsIgnoreCase(sub_kpi) || "pg_read_hits".equalsIgnoreCase(sub_kpi) || "pg_write_hits".equalsIgnoreCase(sub_kpi)){
            if("pg_usage".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.UTILIZATION),1)";
                subWhere = " and c.UTILIZATION>=0 ";
            } else if("pg_io".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.TOTAL_IO),1)";
                subWhere = " and c.TOTAL_IO>=0 ";
            } else if("pg_response".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.TOTAL_TIME),1)";
                subWhere = " and c.TOTAL_TIME>=0 ";
            } else if("pg_transfer".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.TOTAL_KB),1)";
                subWhere = " and c.TOTAL_KB>=0 ";
            } else if("pg_read_hits".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.READ_HITS),1)";
                subWhere = " and c.READ_HITS>=0 ";
            } else if("pg_write_hits".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.WRITE_HITS),1)";
                subWhere = " and c.WRITE_HITS>=0 ";
            }
            sql = "select a.ID as SUBSYSTEM_ID,a.NAME as SUBSYSTEM_NAME,b.ID,b.NAME,"+column+" as VAL " +
                "from T_Res_Storage_Subsystem a,T_Res_RaidGroup b,T_Prf_Dsraidgroup c "+
                "where a.ID=b.SUBSYSTEM_ID and b.ID=c.ELEMENT_ID " +subWhere+
                timescope +
                idscope +
                " group by b.ID,b.NAME " +
                "order by VAL desc";
            System.out.println(sql);
        }else if("chp_usage".equalsIgnoreCase(sub_kpi)||"dkp_usage".equalsIgnoreCase(sub_kpi)){
            if("chp_usage".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.UTILIZATION),1)";
                subWhere = " and c.UTILIZATION>=0 and c.ELEMENT_TYPE='CHP' ";
            } else if("dkp_usage".equalsIgnoreCase(sub_kpi)) {
                column = "ROUND("+math+"(c.UTILIZATION),1)";
                subWhere = " and c.UTILIZATION>=0 and c.ELEMENT_TYPE='DKP' ";
            }
            sql = "select a.ID as SUBSYSTEM_ID,a.NAME as SUBSYSTEM_NAME,b.ID,b.NAME,"+column+" as VAL " +
                "from T_Res_Storage_Subsystem a,T_Res_Controller b,T_Prf_Controller c "+
                "where a.ID=c.SUBSYSTEM_ID and b.ID=c.ELEMENT_ID " +subWhere+
                timescope +
                idscope +
                " group by b.ID,b.NAME " +
                "order by VAL asc";
        }
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> results = sqlQuery.setMaxRows(limit).findList();
        ObjectNode serie = series.addObject();
        serie.put("id", sub_kpi);
        serie.put("name", sub_kpi);
        ArrayNode data = serie.putArray("data");
        for(SqlRow row : results){
            categories.add(row.getString("NAME"));
            data.add(row.getDouble("VAL"));
        }
    }

}
