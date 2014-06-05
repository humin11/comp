package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;

public class Storages extends Controller {

    public static Result index() {
        String id = request().getQueryString("id");
        ObjectNode device = Json.newObject();
        device.put("id",id);
        device.put("name",id);
        device.put("icon","hitachi_small.png");
        return ok(views.html.storage.index.render(device));
    }

    public static Result summary() {
        String id = request().getQueryString("id");
        ObjectNode device = Json.newObject();
        device.put("id",id);
        device.put("name",id);
        return ok(views.html.storage.summary.render(device));
    }

    public static Result port() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id,"cfg_fcport",10));
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

}
