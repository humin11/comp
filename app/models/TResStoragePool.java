/**
 * File Name：TResStoragePool.java
 *
 * Version：
 * Date：2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import com.fasterxml.jackson.databind.JsonNode;
import models.core.ResModel;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResStoragePool
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 上午11:42:45
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 上午11:42:45
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_STORAGE_POOL")
public class TResStoragePool extends GenericModel {
	public Long TOTAL_MANAGED_SPACE;
	public Long REMAINING_MANAGED_SPACE;
	/**
	 * 0 - Not Primordial <br />
	 * 1 - Primordial
	 */
	public Boolean PRIMORDIAL;
    public String POOL_ID;
	public Long CAPACITY;
    public Long FREE_CAPACITY;
	public Integer EXTENT_SIZE;
	public Short NATIVE_STATUS;
	public Long TOTAL_AVAILABLE_SPACE;
	public Short ELEMENT_TYPE;
	public String RAID_LEVEL;
	public String INSTANCE_ID;
	public Integer LSS;
	public Short CONFIG;
	public Long RAID_GROUP_ID;
	public Short FORMAT;
	public Long SURFACED_LUN_CAP;
	public Long UNSURFACED_LUN_CAP;
	public Integer DATA_REDUNDANCY_MIN;
	public Integer DATA_REDUNDANCY_MAX;
	public Integer DATA_REDUNDANCY_DEF;
	public Integer PCK_REDUNDANCY_MIN;
	public Integer PCK_REDUNDANCY_MAX;
	public Integer PCK_REDUNDANCY_DEF;
	public Integer DELTA_RES_MIN;
	public Integer DELTA_RES_MAX;
	public Integer DELTA_RES_DEF;
	public Short RANK_GROUP;
	public Short CLASS_NAME_ID;
	/**
	 * 0 - Not SE POOL <br />
	 * 1 - SE POOL
	 */
	public Short IS_SE_POOL;
	public Double CONFIGURED_SPACE;
	public Short IS_ENCRYPTED;
	public Short IS_ENCRYPTABLE;
	public Short IS_SOLID_STATE;

    public static Model.Finder<String, TResStoragePool> find = new Model.Finder<String, TResStoragePool>(
            String.class, TResStoragePool.class
    );

    public static List<TResStoragePool> findBySubsystemId(String subsystemId){
        return find.where().eq("SUBSYSTEM_ID",subsystemId).findList();
    }

    public static TResStoragePool findById(String id){
        return find.byId(id);
    }

    public static void create(JsonNode node){
        TResStoragePool obj = find.where().eq("NAME",node.get("NAME").asText()).eq("INSTANCE_ID",node.get("INSTANCE_ID").asText()).eq("SUBSYSTEM_NAME", node.get("SUBSYSTEM_NAME").asText()).findUnique();
        if(obj==null){
            obj = new TResStoragePool();
            obj = Json.fromJson(node, TResStoragePool.class);
            obj.save();
        } else {
            String id = obj.ID;
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
