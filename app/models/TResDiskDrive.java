package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Iterator;

/**
 * Created by Humin on 4/28/14.
 */
@Entity
@Table(name="t_res_storage_disk")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResDiskDrive extends AssetModel {

    public String instance_id;

    public String device_id;

    public Boolean primordial;

    public Boolean segential_access;

    public Long total_managed_space;

    public Long capacity;

    public Long remaining_managed_space;

    public Long used_capacity;

    public Long free_capacity;

    public Long block_size;

    public Long number_blocks;

    public Long consumable_blocks;

    public int data_redundancy;

    public int delta_reservation;

    public int package_redundancy;

    public String extent_status;

    public String raid_level;

    public int speed;

    public String slot;

    public String disk_type;

    public Boolean is_spare;

    public String emc_volume_attributes_description;

    public String emc_volume_attributes_description2;


    public static Finder<Long, TResDiskDrive> find = new Finder<Long, TResDiskDrive>(
            Long.class, TResDiskDrive.class
    );

    public static void create(JsonNode node){
        TResDiskDrive obj = find.where().eq("name",node.get("name").asText()).eq("system_name", node.get("system_name").asText()).findUnique();
        if(obj==null){
            obj = new TResDiskDrive();
            obj = Json.fromJson(node, TResDiskDrive.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResDiskDrive.class);
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
