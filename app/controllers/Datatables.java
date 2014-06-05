package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
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
            options = getFCPortCfg(id);
        else if("cfg_raidgroup".equals(model))
            options = getRaidGroupCfg(id);
        else if("cfg_volume".equals(model))
            options = getVolumeCfg(id);
        else if("cfg_disk".equals(model))
            options = getDiskCfg(id);
        else if("alarm".equals(model))
            options = getAlarm(id, title, start_time, end_time);
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

    private static ObjectNode getFCPortCfg(String id) {
        String[] kpiColumns = {"名称","WWN","类型","端口号","Speed"};
        List<TResPort> ports = TResPort.findBySubsystemId(id);
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        for (String colname : kpiColumns)
            cols.add(colname);
        for(TResPort port : ports){
            ArrayNode obj = rows.addArray();
            obj.add(port.ELEMENT_NAME);
            obj.add(Format.splitWWN(port.NAME));
            obj.add(port.USAGE_RESTRICTION == null ? "FICON": "Fibre");
            obj.add(port.PORT_NUMBER == null ? "no data" : port.PORT_NUMBER);
            obj.add(port.PORT_SPEED == null ? "no data" : Format.parserCapacity(port.PORT_SPEED));
        }
        return options;
    }

    private static ObjectNode getRaidGroupCfg(String id) {
        String[] kpiColumns = {"名称","Raid Level","容量","类型","卷数量"};
        List<TResRaidGroup> raidgroups = TResRaidGroup.findBySubsystemId(id);
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        for (String colname : kpiColumns)
            cols.add(colname);
        for(TResRaidGroup raidgroup : raidgroups){
            ArrayNode obj = rows.addArray();
            obj.add(raidgroup.NAME);
            obj.add(raidgroup.RAID_LEVEL);
            obj.add(Format.parserCapacity(raidgroup.DDM_CAP));
            obj.add("FC");
            obj.add(TResRaidGroup2Vol.countByRaidGroupId(raidgroup.ID));
        }
        return options;
    }

    private static ObjectNode getVolumeCfg(String id) {
        String[] kpiColumns = {"名称","Raid Level","容量","类型","Raid组","Num of Blocks"};
        List<TResStorageVolume> volumes = TResStorageVolume.findBySubsystemId(id);
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        for (String colname : kpiColumns)
            cols.add(colname);
        for(TResStorageVolume volume : volumes){
            ArrayNode obj = rows.addArray();
            obj.add(volume.NAME);
            obj.add(volume.RAID_LEVEL == null ? "no data":volume.RAID_LEVEL);
            obj.add(Format.parserCapacity(volume.CAPACITY));
            obj.add("FC");
            TResRaidGroup2Vol rd2v = null;
            obj.add(rd2v == null ? "no data" : TResRaidGroup.findById(rd2v.RAIDGROUP_ID).NAME);
            obj.add(volume.NUMBER_OF_BLOCKS == null?"no data":volume.NUMBER_OF_BLOCKS+"");
        }
        return options;
    }

    private static ObjectNode getDiskCfg(String id) {
        String[] kpiColumns = {"名称","Raid Level","容量","类型","厂商","序列号","Slot"};
        List<TResDisk> disks = TResDisk.findBySubsystemId(id);
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        for (String colname : kpiColumns)
            cols.add(colname);
        for(TResDisk disk : disks){
            ArrayNode obj = rows.addArray();
            obj.add(disk.NAME);
            obj.add(disk.RAID_LEVEL == null ? "no data":disk.RAID_LEVEL);
            obj.add(Format.parserCapacity(disk.CAPACITY));
            obj.add("FC");
            obj.add(TResVendor.findById(disk.VENDOR_ID).NAME);
            obj.add(disk.SERIAL_NUMBER);
            obj.add(disk.SLOT);
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

    private static ObjectNode getAlarm(String id, String title, String start_time, String end_time) {
        String[] testSubsystems = {"USPV.29846", "USPV.29416", "VSP.90873"};
        String[] severity = {"Fatal","Critical","Major","Minor","Warning"};
        String[] colors = {"btn-dark-gray","btn-danger","btn-warning","btn-info","btn-light-green"};
        int count = 100;
        Random random = new Random();
        ObjectNode options = Json.newObject();
        ArrayNode cols = options.putArray("cols");
        ArrayNode rows = options.putArray("rows");
        cols.add("设备");
        cols.add("级别");
        cols.add("描述");
        cols.add("分类");
        cols.add("开始时间");
        for(int i = 0; i < count; i++){
            ArrayNode obj = rows.addArray();
            obj.add(testSubsystems[random.nextInt(3)]);
            int r = random.nextInt(5);
            obj.add("<a class='btn btn-sm "+colors[r]+" btn-labeled'>"+severity[r]+"</a>");
            obj.add("V1TRAP[reqestID=0,timestamp=0:00:43.14,enterprise=1.3.6.1.4.1.232,genericTrap=6,specificTrap=3034, VBS[1.3.6.1.2.1.1.5.0 = STOR_HY_SERVER1;");
            obj.add("STORAGE");
            obj.add(Format.parseString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        return options;
    }

}
