package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.TResPort;
import models.TResSwitch;
import models.TResVendor;
import play.libs.Json;
import play.mvc.*;

import java.util.List;

public class Switchs extends Controller {

    public static Result index() {
        String id = request().getQueryString("id");
        TResSwitch tResSwitch = TResSwitch.findById(id);
        ObjectNode device = (ObjectNode)Json.toJson(tResSwitch);
        String vendorName = tResSwitch.VENDOR;
        if(vendorName.contains("Brocade"))
            device.put("ICON","brocade.png");
        else if(vendorName.contains("Cisco"))
            device.put("ICON","cisco_small.png");
        return ok(views.html.switchs.index.render(device));
    }

    public static Result summary() {
        String id = request().getQueryString("id");
        TResSwitch tResSwitch = TResSwitch.findById(id);
        ObjectNode device = (ObjectNode)Json.toJson(tResSwitch);
        device.put("N_PORT", TResPort.findBySubsystemId(id).size());
        return ok(views.html.switchs.summary.render(device));
    }

    public static Result port() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id, "cfg_port", 10));
    }


    public static Result json() {
        List<TResSwitch> switchList= TResSwitch.findAll();
        ArrayNode json = (ArrayNode)Json.toJson(switchList);
        return ok(json);
    }


}
