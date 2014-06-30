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

    public String INSTANCE_ID;

    public String DEVICE_ID;

    public Boolean PRIMORDIAL;

    public Boolean SEGENTIAL_ACCESS;

    public Long TOTAL_MANAGED_SPACE;

    public Long CAPACITY;

    public Long REMAINING_MANAGED_SPACE;

    public Long USED_CAPACITY;

    public Long FREE_CAPACITY;

    public Long BLOCK_SIZE;

    public Long NUMBER_BLOCKS;

    public Long CONSUMABLE_BLOCKS;

    public int DATA_REDUNDANCY;

    public int DELTA_RESERVATION;

    public int PACKAGE_REDUNDANCY;

    public String EXTENT_STATUS;

    public String RAID_LEVEL;

    public int SPEED;

    public String SLOT;

    public String DISK_TYPE;

    public Boolean IS_SPARE;

    public String EMC_VOLUME_ATTRIBUTES_DESCRIPTION;

    public String EMC_VOLUME_ATTRIBUTES_DESCRIPTION2;


    public static Finder<String, TResDiskDrive> find = new Finder<String, TResDiskDrive>(
            String.class, TResDiskDrive.class
    );

    public static void create(JsonNode node){
        TResDiskDrive obj = find.where().eq("NAME",node.get("NAME").asText()).eq("SUBSYSTEM_NAME", node.get("SUBSYSTEM_NAME").asText()).findUnique();
        if(obj==null){
            obj = new TResDiskDrive();
            obj = Json.fromJson(node, TResDiskDrive.class);
            obj.save();
        } else {
            String id = obj.ID;
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
