/**
 * File Name：TResDiskArray.java
 *
 * Version：
 * Date：2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ResModel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResDiskArray
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午3:59:37
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午3:59:37
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_DISK_ARRAY",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResDiskArray extends ResModel {
	public String MANUFACTURER;
	public String FIRMWARE_VERISION;
	public Integer CACHE_MB;
	public Long DISK_CAPACITY;
	public Long DISK_FREE_SPACE;
	public Timestamp LAST_PROBE_TIME;
	public Short PROBE_STATUS;
	public Integer GROUP_ID;
	public Integer PROBING_COMP_ID;
	public Integer NVS_MB;
	public Long VS_CAPACITY;
	public Long VS_FREESPACE;
	public Long DG_FREECAP;
	public Long LGLDISK_ASSIGNED_CAPACITY;
	public Long LUN_CAPACITY_ZOS;
}
