package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;

public class Storages extends Controller {

    public static Result index() {
        String id = request().getQueryString("id");
        ObjectNode subsystem = Json.newObject();
        subsystem.put("id",id);
        subsystem.put("name",id);
        return ok(views.html.storage.index.render(subsystem));
    }

    public static Result summary() {
        String id = request().getQueryString("id");
        ObjectNode subsystem = Json.newObject();
        subsystem.put("id",id);
        subsystem.put("name",id);
        return ok(views.html.storage.summary.render(subsystem));
    }

    public static Result port() {
        String id = request().getQueryString("id");
        ObjectNode subsystem = Json.newObject();
        subsystem.put("id",id);
        subsystem.put("name",id);
        return ok(views.html.storage.port.render(subsystem));
    }

}
