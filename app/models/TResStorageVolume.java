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
@Table(name="t_res_storage_volume")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResStorageVolume extends GenericModel {

    public String instance_id;

    public String device_id;

    public String pool_id;

    public String pool_name;

    public String pool_instance;

    public Boolean primordial;

    public Boolean segential_access;

    public int access;

    public Boolean is_mapped;

    public Boolean is_underlying_redundancy;

    public Boolean is_composite;

    public Boolean is_thin_provisioned;

    public String controller_name;

    public Long total_managed_space;

    public Long capacity;

    public Long remaining_managed_space;

    public Long used_capacity;

    public Long free_capacity;

    public Long block_size;

    public Long number_blocks;

    public int data_redundancy;

    public int delta_reservation;

    public int package_redundancy;

    public String extent_status;

    public String raid_level;

    public String emc_volume_attributes_description;

    public String emc_volume_attributes_description2;

    public Long consumable_blocks;

    public static Model.Finder<Long, TResStorageVolume> find = new Model.Finder<Long, TResStorageVolume>(
            Long.class, TResStorageVolume.class
    );

    public static void create(JsonNode node){
        TResStorageVolume obj = find.where().eq("name",node.get("name").asText()).eq("system_name", node.get("system_name").asText()).findUnique();
        if(obj==null){
            obj = new TResStorageVolume();
            obj = Json.fromJson(node, TResStorageVolume.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResStorageVolume.class);
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
