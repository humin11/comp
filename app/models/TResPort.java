package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Iterator;

/**
 * Created by Humin on 4/28/14.
 */
@Entity
@Table(name="t_res_port")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResPort extends GenericModel {

    public String permanent_address;

    public String device_id;

    public String usage_restriction;

    public Long speed;

    public Long max_speed;

    public String port_type;

    public String link_technology;

    public String subsystem_name;

    //storage/tape/switch/host
    public String device_type;

    //controller/sfp/blade/hba
    public String subdevice_type;

    public Long supported_maximum_transmission_unit;

    public static Model.Finder<Long, TResPort> find = new Model.Finder<Long, TResPort>(
            Long.class, TResPort.class
    );

    public static void create(JsonNode node){
        TResPort obj = find.where().eq("name",node.get("name").asText()).findUnique();
        if(obj==null){
            obj = new TResPort();
            obj = Json.fromJson(node, TResPort.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResPort.class);
            obj.update(id);
        }
    }

    public static void createAll(JsonNode nodes){
        if(nodes.isArray()) {
            Iterator<JsonNode> it = nodes.elements();
            JsonNode node = null;
            while (it.hasNext()) {
                node = it.next();
                create(node);
            }
        } else {
            create(nodes);
        }
    }
}
