package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.util.Iterator;

/**
 * Created by Humin on 4/26/14.
 */
@Entity
@Table(name="t_res_storagesystem")
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResStorageSystem extends AssetModel {

    public String nvsram_version;

    public int cache_block;

    public static Model.Finder<Long, TResStorageSystem> find = new Model.Finder<Long, TResStorageSystem>(
            Long.class, TResStorageSystem.class
    );

    public static void create(JsonNode node){
        TResStorageSystem storage = find.where().eq("name",node.get("name").asText()).findUnique();
        if(storage==null){
            storage = new TResStorageSystem();
            storage = Json.fromJson(node, TResStorageSystem.class);
            storage.save();
        } else {
            String id = storage.ID;
            storage = Json.fromJson(node, TResStorageSystem.class);
            storage.update(id);
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
