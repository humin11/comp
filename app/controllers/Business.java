package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Business extends Controller {

    public static Result index() {
        return ok(views.html.business.index.render());
    }

    public static Result json(){
        List<TResApplication> apps = TResApplication.findAll();
        ArrayNode json = (ArrayNode)Json.toJson(apps);
        return ok(json);
    }

    public static Result add(){
        ObjectNode params = (ObjectNode)request().body().asJson();
        long capacity = 0L;
        int volcounts = 0;
        String subsystemId = params.get("SUBSYSTEM_ID").asText();
        String[] hostgroups = params.get("HOSTGROUP").asText().split(",");
        String appId = UUID.randomUUID().toString();
        String appName = params.get("NAME").asText();
        for(int i = 0;i < hostgroups.length;i++){
            String hostgroup = hostgroups[i];
            HashMap<String,String> volMap = new HashMap<String,String>();
            List<TResLunMapping> lunmappingList = TResLunMapping.findHostGroupBySubsystemId(subsystemId,hostgroup);
            for(TResLunMapping lunmapping : lunmappingList){
                if(volMap.containsKey(lunmapping.VOLUME_ID))
                    continue;
                else
                    volMap.put(lunmapping.VOLUME_ID,lunmapping.VOLUME_ID);
                volcounts++;
                TResStorageVolume volume = TResStorageVolume.findById(lunmapping.VOLUME_ID);
                capacity+=volume.CAPACITY;
                TResApplication2Lun app2lun = new TResApplication2Lun();
                app2lun.APPLICATION_ID = appId;
                app2lun.APPLICATION_NAME = appName;
                app2lun.HOSTGROUP = lunmapping.HOST_NAME;
                app2lun.VOLUME_ID = lunmapping.VOLUME_ID;
                app2lun.SUBSYSTEM_ID = subsystemId;
                app2lun.save();
            }
        }
        params.put("CAPACITY",capacity);
        params.put("N_VOL",volcounts);
        params.put("ID",appId);
        params.remove("SUBSYSTEM_ID");
        params.remove("HOSTGROUP");
        TResApplication app = Json.fromJson(params,TResApplication.class);
        app.save();
        return ok("");
    }
}
