/**
 * File Name：TResController.java
 *
 * Version：
 * Date：2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.core.ResModel;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Iterator;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResController
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午3:48:38
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午3:48:38
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_CONTROLLER",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_NAME"})})
public class TResController extends AssetModel {
	public Integer SLOT;
    public Integer MEMORY_SIZE;
    public Integer NUM_PORTS;
    public Integer INSTANCE_NUMBER;
	public Integer BUS_NUMBER;
	public String DRIVER_NAME;
	public String DRIVER_DESCRIPTION;
	public Integer TARGET;
	public String TYPE;
	public String LOOPS;
	public String ADAPTER_PAIR;
	public String ARRAY;
	public String WWN;
	public String FIRMWARE_VERSION;
	public String PHYSICAL_PACKAGE_ID;
	public Short USAGE_RESTRICTION;

    public String POWER_CAPACITY;

    public static Model.Finder<Long, TResController> find = new Model.Finder<Long, TResController>(
            Long.class, TResController.class
    );

    public static void create(JsonNode node){
        TResController obj = find.where().eq("NAME",node.get("NAME").asText()).eq("SYSTEM_NAME", node.get("SYSTEM_NAME").asText()).findUnique();
        if(obj==null){
            obj = new TResController();
            obj = Json.fromJson(node, TResController.class);
            obj.save();
        } else {
            Long id = obj.ID;
            obj = Json.fromJson(node, TResController.class);
            obj.update(id);
        }
    }

    public static void createAll(JsonNode nodes){
        Iterator<JsonNode> it = nodes.elements();
        JsonNode node = null;
        while(it.hasNext()){
            node = it.next();
            create(node);
        }
    }

}
