/**
 * File Name：TResPort.java
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

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResPort
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午2:53:22
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午2:53:22
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_PORT")
@Access(AccessType.FIELD)
public class TResPort extends GenericModel {
	public Integer SLOT_NUMBER;

    public String PERMANENT_ADDRESS;

    public String DEVICE_ID;

	public String PORT_NUMBER;

    public String PORT_INDEX;

    public Long PORT_SPEED;

    public Long MAX_SPEED;

    public String PORT_TYPE;

    public String FUNCTION_TYPE;

    //Eg. Storage, Switch, Host
    public String DEVICE_TYPE;

    //Eg. Controller, HBA, Blade, SFP.
    public String SUBDEVICE_TYPE;

    //Record the parent component ID,eg. Controller ID, HBA ID or Blade ID
    public Long NODE_ID;

    public Integer DISPLAY_ICON_TYPE;

    public Integer RIO_LOOP_ID;

    public Integer DETECTABLE;

    public String LOCATION_ID;

    public String USAGE_RESTRICTION;

    public Integer ACTIVE_FC4TYPES;

    public String IS_LICENSED;

    public String IS_VIRTUAL;

    public String LINK_TECHNOLOGY;

    public Long SUPPORTED_MAXIMUM_TRANSMISSION_UNIT;


    public static Model.Finder<String, TResPort> find = new Model.Finder<String, TResPort>(
            String.class, TResPort.class
    );

    public static List<TResPort> findBySubsystemId(String subsystemId){
        return find.where().eq("SUBSYSTEM_ID",subsystemId).findList();
    }

    public static TResPort findById(String id){
        return find.byId(id);
    }

    public static void create(JsonNode node){
        TResPort obj = find.where().eq("name",node.get("name").asText()).findUnique();
        if(obj==null){
            obj = new TResPort();
            obj = Json.fromJson(node, TResPort.class);
            obj.save();
        } else {
            String id = obj.ID;
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
