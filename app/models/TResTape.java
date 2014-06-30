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
@Table(name="t_res_tape")
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResTape extends AssetModel {

    public static Finder<String, TResTape> find = new Finder<String, TResTape>(
            String.class, TResTape.class
    );

    public static void create(JsonNode node){
        TResTape obj = find.where().eq("name",node.get("name").asText()).findUnique();
        if(obj==null){
            obj = new TResTape();
            obj = Json.fromJson(node, TResTape.class);
            obj.save();
        } else {
            String id = obj.ID;
            obj = Json.fromJson(node, TResTape.class);
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
