package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Iterator;

/**
 * Created by Humin on 4/28/14.
 */
@Entity
@Table(name="t_res_storage_pool")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResStoragePool extends GenericModel {

    public String instance_id;

    public String pool_id;

    public Boolean primordial;

    public Long total_managed_space;

    public Long capacity;

    public Long remaining_managed_space;

    public Long free_capacity;

    public Long block_size;

    public Long number_blocks;

    public String raid_level;

    public Long consumable_blocks;

    public static Model.Finder<Long, TResStoragePool> find = new Model.Finder<Long, TResStoragePool>(
            Long.class, TResStoragePool.class
    );

    public static void create(JsonNode node){
        TResStoragePool obj = find.where().eq("name",node.get("name").asText()).eq("instance_id",node.get("instance_id").asText()).eq("system_name", node.get("system_name").asText()).findUnique();
        if(obj==null){
            obj = new TResStoragePool();
            obj = Json.fromJson(node, TResStoragePool.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResStoragePool.class);
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
