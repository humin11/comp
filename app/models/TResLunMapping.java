/**
 * File Name：TResLunMapping.java
 *
 * Version：
 * Date：2012-3-3
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import play.db.ebean.Model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResLunMapping
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:45:59
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:45:59
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_LUN_MAPPING",uniqueConstraints={@UniqueConstraint(columnNames={"VOLUME_ID","HOST_NAME","HBA_WWN"})})
public class TResLunMapping {
	@Id
	public String ID;
	public String NAME;
	public String OPERATIONAL_STATUS;
	public String STATUS;
	public String VOLUME_ID;
	public String FCPORT_ID;
	public String HOST_NAME;
	public String OS_TYPE;
	public String HBA_NAME;
	public String HBA_WWN;
	public String HBA_ID;
	public String PV_ID;
	public String SUBSYSTEM_ID;
	public Timestamp DISCOVERED_TIME; 
	public Timestamp UPDATE_TIME;
	/**
	 * The time of something changed(E.g. Status changed) 
	 */
	public Timestamp CHANGED_TIME;

    public static Model.Finder<String, TResLunMapping> find = new Model.Finder<String, TResLunMapping>(
            String.class, TResLunMapping.class
    );

    public static List<TResLunMapping> findAll() { return find.all(); }

    public static List<TResLunMapping> findBySubsystemId(String id) {
        return find.where().eq("SUBSYSTEM_ID",id).setMaxRows(2000).orderBy("HOST_NAME").findList();
    }

    public static List<TResLunMapping> findHostGroupBySubsystemId(String id,String hostname) {
        return find.where().eq("SUBSYSTEM_ID",id).eq("HOST_NAME",hostname).findList();
    }

}
