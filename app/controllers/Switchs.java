package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;

public class Switchs extends Controller {

    public static Result index() {
        String id = request().getQueryString("id");
        ObjectNode device = Json.newObject();
        device.put("id", id);
        device.put("name", id);
        device.put("icon", "cisco_small.png");
        return ok(views.html.switchs.index.render(device));
    }

    public static Result summary() {
        String id = request().getQueryString("id");
        ObjectNode device = Json.newObject();
        device.put("id", id);
        device.put("name", id);
        return ok(views.html.switchs.summary.render(device));
    }

    public static Result port() {
        String id = request().getQueryString("id");
        return ok(views.html.widgets.table.render(id, "cfg_port", 10));
    }


}
