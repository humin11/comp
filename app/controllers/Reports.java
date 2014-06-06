package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.api.Play;
import play.api.templates.Xml;
import play.api.templates.XmlFormat;
import play.libs.Json;
import play.libs.XML;
import play.mvc.*;
import play.templates.TemplateMagic;
import scala.Console;
import utils.Format;
import utils.Highcharts;
import views.xml.*;

import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Reports extends Controller {


    public static Result daily() {
        try {
            File template = Play.current().getFile("/public/reports/boc_daily.docx");
            FileReader fis = new FileReader(template);
            BufferedReader reader = new BufferedReader(fis);
            StringBuffer lines = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null){
                lines.append(line.trim());
            }
            reader.close();
            String newXml = dailyTempData(lines.toString());
            File newDoc = Play.current().getFile("/public/reports/boc_daily_new.docx");
            if(newDoc.exists())
                newDoc.delete();
            FileWriter writer = new FileWriter(newDoc);
            writer.write(newXml);
            writer.close();
            return ok(newDoc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok();
    }

    private static String dailyTempData(String xml){
        String result = xml;
        String[] subsystemArray = {"29846","29416","90873"};
        String[] kpiArray = {"CHP","DKP","CACHE","PG","TC_TRANSFER","TC_RESPONSE","UR_TRANSFER","UR_RESPONSE"};
        Random rnd = new Random();
        for(String subsystem : subsystemArray){
            for(String kpi : kpiArray){
                String replaceStr = Pattern.quote("${"+subsystem+"_"+kpi+"}");
                result = result.replaceAll(replaceStr, rnd.nextInt(100) + "");
            }
        }
        String today = Format.parseString(new Date(),"yyyy年MM月dd日");
        result = result.replaceAll(Pattern.quote("${timerange}"), "2014年6月4日 00:00 ~ 2014年6月5日 00:00");
        ObjectNode chp_usage = Charts.line("1A3AD92D7D39C98E8C5E4BE05FDBE4DA,364B90F35C542C7810C87BAAE4391A2A", "HDS 前端处理器 繁忙度", "subsystem","chp_usage", "max", "2014-06-04 00:00", "2014-06-05 00:00");
        ObjectNode dkp_usage = Charts.line("1A3AD92D7D39C98E8C5E4BE05FDBE4DA,364B90F35C542C7810C87BAAE4391A2A", "HDS 后端处理器 繁忙度", "subsystem","dkp_usage", "max", "2014-06-04 00:00", "2014-06-05 00:00");
        ObjectNode cache_usage = Charts.line("1A3AD92D7D39C98E8C5E4BE05FDBE4DA,364B90F35C542C7810C87BAAE4391A2A", "HDS Raid组 繁忙度", "subsystem","pg_usage", "max", "2014-06-04 00:00", "2014-06-05 00:00");
        ObjectNode pg_usage = Charts.line("1A3AD92D7D39C98E8C5E4BE05FDBE4DA,364B90F35C542C7810C87BAAE4391A2A", "HDS 缓存 写等待率", "subsystem","cache_write_pending", "max", "2014-06-04 00:00", "2014-06-05 00:00");
        result = fillMaxValue(result,chp_usage);
        result = fillMaxValue(result,dkp_usage);
        result = fillMaxValue(result,cache_usage);
        result = fillMaxValue(result,pg_usage);
        String chart1PNG = Highcharts.export(chp_usage);
        String chart2PNG = Highcharts.export(dkp_usage);
        String chart3PNG = Highcharts.export(cache_usage);
        String chart4PNG = Highcharts.export(pg_usage);
        result = result.replaceAll(Pattern.quote("${chart1}"), chart1PNG);
        result = result.replaceAll(Pattern.quote("${chart2}"), chart2PNG);
        result = result.replaceAll(Pattern.quote("${chart3}"), chart3PNG);
        result = result.replaceAll(Pattern.quote("${chart4}"), chart4PNG);
        return result;
    }

    private static String fillMaxValue(String result,ObjectNode series){
        ArrayNode chpSeries = (ArrayNode)series.findPath("series");
        for(int i=0;i<chpSeries.size();i++) {
            ObjectNode serie = (ObjectNode)chpSeries.get(i);
            String name = serie.findValue("name").asText();
            String serial = name.contains("29846")?"29846":"29416";
            double max = serie.findValue("max").asDouble();
            String replaceStr = Pattern.quote("${"+serial+"_CHP}");
            result = result.replaceAll(replaceStr, max + "");
        }
        return result;
    }

}
