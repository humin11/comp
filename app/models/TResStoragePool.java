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

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name="T_RES_STORAGE_POOL",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResStoragePool extends ResModel {
	public Long TOTAL_MANAGED_SPACE;
	public Long REMAINING_MANAGED_SPACE;
	/**
	 * 0 - Not Primordial <br />
	 * 1 - Primordial
	 */
	public Short PRIMORDIAL;
	public Long CAPACITY;
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
}
