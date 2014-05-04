package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Iterator;

/**
 * Created by Humin on 5/1/14.
 */
@Entity
@Table(name="t_res_controller")
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResController extends AssetModel {

    public int instance_number;
    public int bus_number;
    public String driver_name;
    public String driver_description;
    public int target;
    public String type;
    public String loops;
    public int slot;
    public String adapter_pair;
    public String array;
    public String wwn;
    public String usage_restriction;
    public String power_capacity;

    public int memory_size;

    public int num_ports;

    public static Model.Finder<Long, TResController> find = new Model.Finder<Long, TResController>(
            Long.class, TResController.class
    );

    public static void create(JsonNode node){
        TResController obj = find.where().eq("name",node.get("name").asText()).eq("system_name", node.get("system_name").asText()).findUnique();
        if(obj==null){
            obj = new TResController();
            obj = Json.fromJson(node, TResController.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResController.class);
            obj.update(id);
        }
    }

    public static void createAll(ArrayNode nodes){
        Iterator<JsonNode> it = nodes.elements();
        JsonNode node = null;
        while(it.hasNext()){
            node = it.next();
            create(node);
        }
    }


}
