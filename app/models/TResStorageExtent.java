/**
 * File Name：TResStorageExtent.java
 *
 * Version：
 * Date：2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResStorageExtent
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午12:28:36
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午12:28:36
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_STORAGE_EXTENT",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","DEVICE_ID","SUBSYSTEM_ID"})})
public class TResStorageExtent extends ResModel {
	public Integer NUM_EXTENTS;
	public Integer EXTENT_TYPE;
	public String POOL_ID;
	public Long TOTAL_CAPACITY;
	public Long FREE_SPACE;
	public Integer VOLS_IN_STORAGE_EXTENT;
	public String RANK_ID;
	public String CONFIG;
	public String VOL_GROUP_ID;
	public String GROUP_ID;
	public String DEVICE_ID;
	public Short CREATION_CLASS_NAME_ID;
	public Short SYSTEM_CREATION_CLASS_NAME_ID;
	public Short SYSTEM_NAMES_ID;
	public Integer BLOCK_SIZE;
	public Long NUM_OF_BLOCKS;
	public Long CONSUMABLE_BLOCKS;
	public Integer STORAGE_EXTENT_MODE;
	public String SERIAL_NUMBER;
	public String CONTROLLER_ID;
	public String ARRAY_IDS;
	public Short IS_VIRTUAL;
	public String ENCRYPTION_GROUP_ID;
	public String SLOT_LOCATIOIN;
	public String NODE_ID;
	public String NODE_NAME;
	public String RAID_LEVEL;
	public String RAID_STATUS;
	public Integer FAST_WRITE_STATE;
	public Integer STRIP_SIZE;
	public Short WRITE_VERIFY;
	public Integer SPARE_GOAL;
	public Integer SPARE_PROTECTION_MIN;
	public Integer BALANCED;
}
