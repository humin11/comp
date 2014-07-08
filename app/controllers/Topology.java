package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.*;
import java.util.ArrayList;
import java.util.List;

public class Topology extends Controller {

    public static Result layer1() {
        ObjectNode layer1 = Json.newObject();
        ArrayNode groups = layer1.putArray("groups");
        ArrayNode nodes = layer1.putArray("nodes");
        ArrayNode links = layer1.putArray("links");
        List<TResHost> hosts = TResHost.findAll();
        for(TResHost host : hosts){
            ObjectNode node = nodes.addObject();
            node.put("id",host.ID);
            ObjectNode data = node.putObject("data");
            data.put("name",host.NAME);
            data.put("description",host.NAME);
            data.put("vendor",host.OS_TYPE);
            data.put("type","host");
        }
        List<TResSwitch> switchs = TResSwitch.findAll();
        for(TResSwitch switch1 : switchs){
            ObjectNode node = nodes.addObject();
            node.put("id",switch1.ID);
            ObjectNode data = node.putObject("data");
            data.put("name",switch1.ELEMENT_NAME);
            data.put("description",switch1.ELEMENT_NAME);
            data.put("ip",switch1.IP_ADDRESS);
            data.put("type","switch");
        }
        return ok(layer1);
    }
}
