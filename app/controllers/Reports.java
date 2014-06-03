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
        result = result.replaceAll(Pattern.quote("${timerange}"), today);
        String chartPNG = Highcharts.export(Charts.line("", "IO Usage", "", "", "", ""));
        result = result.replaceAll(Pattern.quote("${chart1}"), chartPNG);
        return result;
    }

}
