package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.libs.Json;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Iterator;

/**
 * Created by Humin on 4/26/14.
 */
@Entity
@Table(name="t_res_switch")
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResSwitch extends AssetModel {

    public String domain_id;
    public String domain_name;

    public String reboot_reason;

    public String switch_id;

    public String switch_role;


    public static Finder<Long, TResSwitch> find = new Finder<Long, TResSwitch>(
            Long.class, TResSwitch.class
    );

    public static void create(JsonNode node){
        TResSwitch obj = find.where().eq("name",node.get("name").asText()).findUnique();
        if(obj==null){
            obj = new TResSwitch();
            obj = Json.fromJson(node, TResSwitch.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResSwitch.class);
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
