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
        String[] hosts = {"VMware","Redhat","CentOS","WindowsServer2008"};
        String[] switchs = {"Brocade","Cisco","FICON"};
        String[] storages = {"USPV_29846","USPV_29416","DMX","FAS3270"};
        for(String host : hosts){
            ObjectNode node = nodes.addObject();
            node.put("id",host);
            ObjectNode data = node.putObject("data");
            data.put("name",host);
            data.put("description",host);
            data.put("vendor",host);
            data.put("ip","127.0.0.1");
            data.put("type","host");
            for(String switch1 : switchs){
                links.addObject().put("id",host+switch1).put("source",host).put("target",switch1);
            }
        }
        for(String switch1 : switchs){
            ObjectNode node = nodes.addObject();
            node.put("id",switch1);
            ObjectNode data = node.putObject("data");
            data.put("name",switch1);
            data.put("description",switch1);
            data.put("vendor",switch1);
            data.put("ip","127.0.0.1");
            data.put("type","switch");
            for(String storage : storages){
                links.addObject().put("id",switch1+storage).put("source",switch1).put("target",storage);
            }
        }
        for(String storage : storages){
            ObjectNode node = nodes.addObject();
            node.put("id",storage);
            ObjectNode data = node.putObject("data");
            data.put("name",storage);
            data.put("description",storage);
            data.put("vendor",storage);
            data.put("ip","127.0.0.1");
            data.put("type","storage");
        }
        return ok(layer1);
    }
}
