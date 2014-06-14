package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.TResRaidGroup;
import models.TResStorageSubsystem;
import models.TResSwitch;
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
        else if("switchs".equalsIgnoreCase(kpi))
            getSwitchPrf(id, sub_kpi, start_time, end_time, series);
        else if("host".equalsIgnoreCase(kpi))
            getHostPrf(id, sub_kpi, start_time, end_time, series);
        else if("business".equalsIgnoreCase(kpi))
            getBusinessPrf(id, sub_kpi,math, start_time, end_time, series);
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
        c.add(Calendar.HOUR, -30);
        String startTime = c.getTimeInMillis()+"";
        String endTime = "";
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyy-MM-dd HH:mm").getTime()+"";
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyy-MM-dd HH:mm").getTime()+"";
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
            if ("".equals(id) || ids.contains(subsystem.ID)) {
                ObjectNode serie = series.addObject();
                serie.put("id", subsystem.ID);
                serie.put("name", subsystem.NAME.length()>15?subsystem.NAME.substring(0,15)+"...":subsystem.NAME);
                serie.put("max",0);
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
                "from T_Res_Storage_Subsystem a,T_Prf_Dsraidgroup c "+
                "where a.ID=c.SUBSYSTEM_ID " + subWhere +
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
        }else if("tc_response".equalsIgnoreCase(sub_kpi)||"ur_response".equalsIgnoreCase(sub_kpi)){
            return;
        }
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> results = sqlQuery.findList();
        for(SqlRow row : results){
            if(serieMap.containsKey(row.getString("ID"))) {
                ObjectNode serie = serieMap.get(row.getString("ID"));
                ArrayNode data = (ArrayNode) serie.findValue("data");
                double max = serie.findValue("max").asDouble();
                if(row.getDouble("VAL")>max)
                    serie.put("max",row.getDouble("VAL"));
                ObjectNode xy = data.addObject();
                xy.put("x", row.getLong("DEV_TIME"));
                xy.put("y", row.getDouble("VAL"));
            }
        }
    }

    private static void getBusinessPrf(String id, String sub_kpi,String math, String start_time, String end_time, ArrayNode series) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -30);
        Date startTime = c.getTime();
        Date endTime = new Date();
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyy-MM-dd HH:mm");
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyy-MM-dd HH:mm");
        }
        String sql = "select a.APPLICATION_NAME,t.stoptime as time,SUM(p.TOTAL_IO/p.INTERVAL_LEN) as total_io,SUM(p.TOTAL_KB/p.INTERVAL_LEN) as total_kb from " +
                "T_Res_Application2Lun a,T_Prf_Dsvol p, V_Prf_TimeStamp t where " +
                "a.VOLUME_ID=p.ELEMENT_ID and " +
                "a.APPLICATION_NAME=:ELEMENT_ID and p.TIME_ID=t.ID and t.stoptime>=:START_TIME and " +
                "t.stoptime<=:END_TIME group by a.APPLICATION_NAME,t.stoptime order by t.stoptime asc";
        System.out.println(sql);
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        sqlQuery.setParameter("ELEMENT_ID",id);
        sqlQuery.setParameter("START_TIME",startTime);
        sqlQuery.setParameter("END_TIME",endTime);
        List<SqlRow> results = sqlQuery.findList();
        ObjectNode serie = series.addObject();
        ArrayNode serieData = serie.putArray("data");
        serie.put("id",id);
        serie.put("name",id);
        for(SqlRow row : results){
            ObjectNode xy = serieData.addObject();
            xy.put("x",row.getDate("time").getTime());
            xy.put("y",row.getDate("total_io").getTime());
        }
    }

    private static void getRaidGroupPrf(String id, String sub_kpi, String start_time, String end_time, ArrayNode series) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -30);
        String startTime = c.getTimeInMillis()+"";
        String endTime = "";
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyy-MM-dd HH:mm").getTime()+"";
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyy-MM-dd HH:mm").getTime()+"";
        }
        String timescope = " and c.DEV_TIME>"+startTime;
        if(!"".equals(endTime))
            timescope += (" and c.DEV_TIME<"+endTime);
        String column = "c.UTILIZATION";
        if("usage".equalsIgnoreCase(sub_kpi)){
            column = "c.UTILIZATION";
        }else if("io".equalsIgnoreCase(sub_kpi)){
            column = "c.TOTAL_IO";
        }else if("io".equalsIgnoreCase(sub_kpi)){
            column = "c.TOTAL_IO";
        }else if("transfer".equalsIgnoreCase(sub_kpi)){
            column = "c.TOTAL_KB";
        }else if("response".equalsIgnoreCase(sub_kpi)){
            column = "c.TOTAL_TIME";
        }else if("read_hits".equalsIgnoreCase(sub_kpi)){
            column = "c.READ_HITS";
        }else if("write_hits".equalsIgnoreCase(sub_kpi)){
            column = "c.WRITE_HITS";
        }
        String sql = "select c.DEV_TIME,c.ELEMENT_ID,"+column+" as VAL " +
                "from T_Prf_Dsraidgroup c "+
                "where c.SUBSYSTEM_ID='"+id+"' " +
                timescope +
                " order by c.DEV_TIME,c.ELEMENT_ID asc";
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> results = sqlQuery.findList();
        HashMap<String,ObjectNode> serieMap = new HashMap<String,ObjectNode>();
        List<TResRaidGroup> rdList = TResRaidGroup.findBySubsystemId(id);
        for (TResRaidGroup rd : rdList) {
            ObjectNode serie = series.addObject();
            serie.put("id", rd.ID);
            serie.put("name", rd.NAME);
            serie.putArray("data");
            serieMap.put(rd.ID, serie);
        }
        for(SqlRow row : results){
            if(serieMap.containsKey(row.getString("ELEMENT_ID"))) {
                ObjectNode serie = serieMap.get(row.getString("ELEMENT_ID"));
                ArrayNode data = (ArrayNode) serie.findValue("data");
                ObjectNode xy = data.addObject();
                xy.put("x", row.getLong("DEV_TIME"));
                xy.put("y", row.getDouble("VAL"));
            }
        }
    }

    private static void getSubsystemPrfTopn(String id,String sub_kpi,String math, String start_time, String end_time,ArrayNode categories, ArrayNode series) {
        int limit = 10;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -30);
        String startTime = c.getTimeInMillis()+"";
        String endTime = "";
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyy-MM-dd HH:mm").getTime()+"";
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyy-MM-dd HH:mm").getTime()+"";
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
            sql = "select a.ID as SUBSYSTEM_ID,a.NAME as SUBSYSTEM_NAME,c.ELEMENT_NAME as NAME,"+column+" as VAL " +
                "from T_Res_Storage_Subsystem a,T_Prf_Dsraidgroup c "+
                "where a.ID=c.SUBSYSTEM_ID " +subWhere+
                timescope +
                idscope +
                " group by c.ELEMENT_NAME " +
                "order by VAL desc";
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

    private static void getSwitchPrf(String id, String sub_kpi,String start_time, String end_time, ArrayNode series) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date startTime = c.getTime();
        Date endTime = new Date();
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyy-MM-dd HH:mm");
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyy-MM-dd HH:mm");
        }
        String sql = "select a.ELEMENT_NAME,t.stoptime as time," +
                "p.PEAK_TX_RATE/p.INTERVAL_LEN as TX_RATE,p.PEAK_RX_RATE/p.INTERVAL_LEN as RX_RATE," +
                "p.SEND_KB/p.INTERVAL_LEN as S_KB,p.RECV_KB/p.INTERVAL_LEN as R_KB," +
                "p.SEND_PKTS/p.INTERVAL_LEN as S_PKTS,p.RECV_PKTS/p.INTERVAL_LEN as R_PKTS from " +
                "T_Res_Switch a,T_Prf_Switch p, V_Prf_TimeStamp t where a.ID=p.ELEMENT_ID and " +
                "p.ELEMENT_ID=:ELEMENT_ID and p.TIME_ID=t.ID and t.stoptime>=:START_TIME and " +
                "t.stoptime<=:END_TIME order by t.stoptime asc";
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        sqlQuery.setParameter("ELEMENT_ID",id);
        sqlQuery.setParameter("START_TIME",startTime);
        sqlQuery.setParameter("END_TIME",endTime);
        List<SqlRow> results = sqlQuery.findList();
        ObjectNode send = series.addObject();
        ArrayNode sendData = send.putArray("data");
        ObjectNode recv = series.addObject();
        ArrayNode recvData = recv.putArray("data");
        if("pkgs".equalsIgnoreCase(sub_kpi)){
            send.put("id", "send_pkgs");
            send.put("name", "发送包");
            recv.put("id", "recv_pkgs");
            recv.put("name", "接收包");
        }else{
            send.put("id", "send_kb");
            send.put("name", "发送数据");
            recv.put("id", "recv_kb");
            recv.put("name", "接收数据");
        }
        for(SqlRow row : results){
            ObjectNode sendXY = sendData.addObject();
            ObjectNode recvXY = recvData.addObject();
            sendXY.put("x",row.getDate("time").getTime());
            recvXY.put("x",row.getDate("time").getTime());
            if("pkgs".equalsIgnoreCase(sub_kpi)){
                sendXY.put("y",row.getDouble("S_PKTS"));
                recvXY.put("y",row.getDouble("R_PKTS"));
            }else{
                sendXY.put("y",row.getDouble("S_KB"));
                recvXY.put("y",row.getDouble("R_KB"));
            }
        }
    }

    private static void getHostPrf(String id, String sub_kpi,String start_time, String end_time, ArrayNode series) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date startTime = c.getTime();
        Date endTime = new Date();
        if(!start_time.equals("")){
            startTime = Format.parseDate(start_time,"yyyy-MM-dd HH:mm");
        }
        if(!end_time.equals("")) {
            endTime = Format.parseDate(end_time, "yyyy-MM-dd HH:mm");
        }
        String sql = "select t.stoptime as time," +
                "SUM(p.READ_IO/p.INTERVAL_LEN) as R_IO,SUM(p.WRITE_IO/p.INTERVAL_LEN) as W_IO," +
                "SUM(p.READ_KB/p.INTERVAL_LEN) as R_KB,SUM(p.WRITE_KB/p.INTERVAL_LEN) as W_KB from " +
                "T_Prf_Hba_port p,V_Prf_TimeStamp t where " +
                "p.SUBSYSTEM_ID=:ELEMENT_ID and p.TIME_ID=t.ID and t.stoptime>=:START_TIME and " +
                "t.stoptime<=:END_TIME group by p.SUBSYSTEM_ID,t.stoptime order by t.stoptime asc";
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        sqlQuery.setParameter("ELEMENT_ID",id);
        sqlQuery.setParameter("START_TIME",startTime);
        sqlQuery.setParameter("END_TIME",endTime);
        List<SqlRow> results = sqlQuery.findList();
        ObjectNode read = series.addObject();
        ArrayNode readData = read.putArray("data");
        ObjectNode write = series.addObject();
        ArrayNode writeData = write.putArray("data");
        if("io".equalsIgnoreCase(sub_kpi)){
            read.put("id", "read_io");
            read.put("name", "读IO");
            write.put("id", "write_io");
            write.put("name", "写IO");
        }else{
            read.put("id", "read_kb");
            read.put("name", "读流量");
            write.put("id", "write_kb");
            write.put("name", "写流量");
        }
        for(SqlRow row : results){
            ObjectNode readXY = readData.addObject();
            ObjectNode writeXY = writeData.addObject();
            readXY.put("x",row.getDate("time").getTime());
            writeXY.put("x",row.getDate("time").getTime());
            if("io".equalsIgnoreCase(sub_kpi)){
                readXY.put("y",row.getDouble("R_IO"));
                writeXY.put("y",row.getDouble("W_IO"));
            }else{
                readXY.put("y",row.getDouble("R_KB"));
                writeXY.put("y",row.getDouble("W_KB"));
            }
        }
    }

}
