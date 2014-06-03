package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import discovery.DiscoverySMI;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        String result = "";
//        DiscoveryVMWare vm = new DiscoveryVMWare();
//
//        DiscoveryVMWare.getHost();


        //DiscoverySMI cs = new DiscoverySMI("http","10.1.11.35","5998","root/lsissi11","admin","admin");
//        cs = new DiscoveryComputerSystem("http","10.1.11.35","5988","root/brocade1","admin","password");
        return ok(dashboard.render());
    }

    public static Result menu(){
        ArrayNode root = Json.newObject().putArray("root");
        ObjectNode c1 = root.addObject();
        c1.put("id",1);
        c1.put("name","配置");
        c1.put("url","");
        c1.put("icon","nasCluster16");
        ArrayNode c1c = c1.putArray("children");
        ObjectNode c11 = c1c.addObject();
        c11.put("id",11);
        c11.put("name","存储阵列");
        c11.put("url","");
        c11.put("icon","xiv16");
        ArrayNode c11c = c11.putArray("children");
        ObjectNode c110 = c11c.addObject();
        c110.put("id",110);
        c110.put("name","概览");
        c110.put("url","/storage");
        c110.put("icon","fa-dashboard");
        ObjectNode c111 = c11c.addObject();
        c111.put("id",111);
        c111.put("name","端口");
        c111.put("url","");
        c111.put("icon","port16");
        ObjectNode c112 = c11c.addObject();
        c112.put("id",112);
        c112.put("name","RaidGroup");
        c112.put("url","");
        c112.put("icon","pool16");
        ObjectNode c113 = c11c.addObject();
        c113.put("id",113);
        c113.put("name","卷");
        c113.put("url","");
        c113.put("icon","thinVolume16");
        ObjectNode c114 = c11c.addObject();
        c114.put("id",114);
        c114.put("name","磁盘");
        c114.put("url","");
        c114.put("icon","disk16");
        ObjectNode c12 = c1c.addObject();
        c12.put("id",12);
        c12.put("name","交换机");
        c12.put("url","");
        c12.put("icon","storwize16");
        ObjectNode c13 = c1c.addObject();
        c13.put("id",13);
        c13.put("name","主机");
        c13.put("url","");
        c13.put("icon","unmanagedServer16");
        ObjectNode c2 = root.addObject();
        c2.put("id",2);
        c2.put("name","拓扑");
        c2.put("url","");
        c2.put("icon","fa-sitemap");
        ObjectNode c3 = root.addObject();
        c3.put("id",3);
        c3.put("name","告警");
        c3.put("url","");
        c3.put("icon","fa-warning");
        ObjectNode c4 = root.addObject();
        c4.put("id",4);
        c4.put("name","报表");
        c4.put("url","");
        c4.put("icon","fa-bar-chart-o");
        return ok(root);
    }

    public static Result dashboard() {
        return ok(dashboard.render());
    }

    public static Result storage() {
        return ok(storage.render());
    }

}
