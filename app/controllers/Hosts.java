package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.*;

import java.util.List;

public class Hosts extends Controller{

    public static Result index() {
        String id = request().getQueryString("id");
        TResHost host = TResHost.findById(id);
        ObjectNode device = (ObjectNode)Json.toJson(host);
        String vendorName = host.OS_TYPE.toLowerCase();
        if(vendorName.indexOf("win") >= 0)
            device.put("ICON","windows_small.png");
        else if(vendorName.indexOf("aix") >= 0)
            device.put("ICON","aix_small.png");
        else if(vendorName.indexOf("rhel") >= 0 || vendorName.indexOf("sles") >= 0)
            device.put("ICON","linux_small.png");
        else if(vendorName.indexOf("zos") >= 0)
            device.put("ICON","ibm_small.png");
        else
            device.put("ICON","windows_small.png");
        device.put("N_PORT", TResPort.findBySubsystemId(id).size());
        return ok(views.html.host.index.render(device));
    }

}
