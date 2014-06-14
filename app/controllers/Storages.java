package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import models.view.VLunMapping;
import play.libs.Json;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

public class Storages extends Controller {

    public static Result index() {
        String id = request().getQueryString("id");
        String tab = request().getQueryString("tab");
        TResStorageSubsystem subsystem = TResStorageSubsystem.findById(id);
        ObjectNode device = (ObjectNode)Json.toJson(subsystem);
        String vendorName = TResVendor.findById(subsystem.VENDOR_ID).NAME.toLowerCase();
        if(vendorName.contains("hitachi"))
            device.put("ICON","hitachi_small.png");
        else if(vendorName.contains("emc"))
            device.put("ICON","emc_small.png");
        else if(vendorName.contains("netapp"))
            device.put("ICON","netapp_small.png");
        else if(vendorName.contains("cisco"))
            device.put("ICON","cisco_small.png");
        return ok(views.html.storage.index.render(device,tab));
    }

    public static Result summary() {
        String id = request().getQueryString("id");
        TResStorageSubsystem subsystem = TResStorageSubsystem.findById(id);
        ObjectNode device = (ObjectNode)Json.toJson(subsystem);
        device.put("N_RAIDGROUP", TResRaidGroup.findBySubsystemId(id).size());
        device.put("N_VOL",TResStorageVolume.findBySubsystemId(id).size());
        device.put("N_DISK", TResDisk.findBySubsystemId(id).size());
        return ok(views.html.storage.summary.render(device));
    }

    public static Result port() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id, "cfg_fcport", 10));
    }

    public static Result cache() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id,"cfg_cache",10));
    }

    public static Result raidgroup() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id,"cfg_raidgroup",10));
    }

    public static Result volume() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id,"cfg_volume",10));
    }

    public static Result disk() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id,"cfg_disk",10));
    }

    public static Result hostgroup() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id,"cfg_hostgroup",10));
    }

    public static Result json() {
        List<TResStorageSubsystem> subsystemList= TResStorageSubsystem.findAll();
        ArrayNode json = (ArrayNode)Json.toJson(subsystemList);
        return ok(json);
    }

    public static Result hostgroupJson(){
        String id = request().getQueryString("id");
        List<String> hostgroup = new ArrayList<String>();
        List<TResLunMapping> lunmappingList = TResLunMapping.findBySubsystemId(id);
        for(TResLunMapping lunmapping : lunmappingList){
            if(!hostgroup.contains(lunmapping.HOST_NAME)) {
                hostgroup.add(lunmapping.HOST_NAME);
            }
        }
        ArrayNode json = (ArrayNode)Json.toJson(hostgroup);
        return ok(json);
    }

}
