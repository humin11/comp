/**
 * File Name：TResRaidGroup.java
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
 * Class Name：TResRaidGroup
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午12:19:15
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午12:19:15
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_RAIDGROUP",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResRaidGroup extends ResModel {
	public String RAID_LEVEL;
	public String LOOPS;
	public String ARRAY;
	public Short WIDTH;
	public String STORAGE_EXTENT_ID;
	public String DEVICE_ADAPTER1;
	public String DEVICE_ADAPTER2;
	public Long DA_PAIR_ID;
	public Long DDM_CAP;
	public String DDM_SPEED;
	public String TAG;
	public Integer NUM_OF_DATADISKS;
}
