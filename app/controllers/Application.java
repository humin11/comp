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
        c11.put("icon","storageSystem16");
        ArrayNode c11c = c11.putArray("children");
        initStorageSubMenu(c11c,"USPV.29846","USPV.29846");
        initStorageSubMenu(c11c,"USPV.29416","USPV.29416");
        initStorageSubMenu(c11c,"VSP.90873","VSP.90873");
        ObjectNode c12 = c1c.addObject();
        c12.put("id",12);
        c12.put("name","交换机");
        c12.put("url","");
        c12.put("icon","iogroup16");
        ArrayNode c12c = c12.putArray("children");
        initSwitchSubMenu(c12c,"FW44","FW44");
        initSwitchSubMenu(c12c,"hy_pro_sw9","hy_pro_sw9");
        ObjectNode c13 = c1c.addObject();
        c13.put("id",13);
        c13.put("name","主机");
        c13.put("url","");
        c13.put("icon","cluster16");
        ArrayNode c13c = c13.putArray("children");
        initHostSubMenu(c13c,"BA0226","BA0226");
        initHostSubMenu(c13c,"BA0126","BA0126");
        ObjectNode c2 = root.addObject();
        c2.put("id",2);
        c2.put("name","拓扑");
        c2.put("url","/topology");
        c2.put("icon","fa-sitemap");
        ObjectNode c3 = root.addObject();
        c3.put("id",3);
        c3.put("name","告警");
        c3.put("url","/alarm");
        c3.put("icon","fa-warning");
        ObjectNode c4 = root.addObject();
        c4.put("id",4);
        c4.put("name","报表");
        c4.put("url","/reports");
        c4.put("icon","fa-bar-chart-o");
        return ok(root);
    }

    private static void initStorageSubMenu(ArrayNode storages,String id,String name){
        ObjectNode storage = storages.addObject();
        storage.put("id",id);
        storage.put("name",name);
        storage.put("url","");
        storage.put("icon","ds800016");
        ArrayNode storageSub = storage.putArray("children");
        ObjectNode dashboard = storageSub.addObject();
        dashboard.put("id",110);
        dashboard.put("name","概览");
        dashboard.put("url","/storage?id="+id);
        dashboard.put("icon","fa-dashboard");
        ObjectNode port = storageSub.addObject();
        port.put("id",111);
        port.put("name","端口");
        port.put("url","/storage/port?id="+id);
        port.put("icon","port16");
        ObjectNode raidgroup = storageSub.addObject();
        raidgroup.put("id",112);
        raidgroup.put("name","RaidGroup");
        raidgroup.put("url","/storage/raidgroup?id="+id);
        raidgroup.put("icon","pool16");
        ObjectNode volume = storageSub.addObject();
        volume.put("id",113);
        volume.put("name","卷");
        volume.put("url","/storage/volume?id="+id);
        volume.put("icon","thinVolume16");
        ObjectNode disk = storageSub.addObject();
        disk.put("id",114);
        disk.put("name","磁盘");
        disk.put("url","/storage/disk?id="+id);
        disk.put("icon","disk16");
    }

    private static void initSwitchSubMenu(ArrayNode switchs,String id,String name){
        ObjectNode switch1 = switchs.addObject();
        switch1.put("id",id);
        switch1.put("name",name);
        switch1.put("url","");
        switch1.put("icon","storwize16");
        ArrayNode switchSub = switch1.putArray("children");
        ObjectNode dashboard = switchSub.addObject();
        dashboard.put("id",110);
        dashboard.put("name","概览");
        dashboard.put("url","/switch?id="+id);
        dashboard.put("icon","fa-dashboard");
        ObjectNode port = switchSub.addObject();
        port.put("id",111);
        port.put("name","端口");
        port.put("url","/switch/port?id="+id);
        port.put("icon","zoneSet16");
    }

    private static void initHostSubMenu(ArrayNode hosts,String id,String name){
        ObjectNode host = hosts.addObject();
        host.put("id",id);
        host.put("name",name);
        host.put("url","");
        host.put("icon","unmanagedServer16");
        ArrayNode hostSub = host.putArray("children");
        ObjectNode dashboard = hostSub.addObject();
        dashboard.put("id",110);
        dashboard.put("name","概览");
        dashboard.put("url","/host?id="+id);
        dashboard.put("icon","fa-dashboard");
        ObjectNode port = hostSub.addObject();
        port.put("id",111);
        port.put("name","端口");
        port.put("url","/host/port?id="+id);
        port.put("icon","hba16");
    }

    public static Result dashboard() {
        return ok(dashboard.render());
    }

}
