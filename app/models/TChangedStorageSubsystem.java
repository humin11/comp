/**
 * File Name TResStorageSubsystem.java
 *
 * Version
 * Date 2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ChangedModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResStorageSubsystem
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-28 下午4:59:14
 * Last Modified By：tigaly
 * Last Modified：2012-2-28 下午4:59:14
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_CHANGED_STORAGE_SUBSYSTEM",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TChangedStorageSubsystem extends ChangedModel {
	public Long RAW_CAPACITY;
	public Long ASSIGNED_CAPACITY;	
	public Long UNASSIGNED_CAPACITY;
	public Long ALLOCATED_CAPACITY;
	public Long UNALLOCATED_CAPACITY;
	public Long MAPPING_CAPACITY;
	public Long UNMAPPING_CAPACITY;
	public Long USED_CAPACITY;
	public Long UNUSED_CAPACITY;
	public Long CACHE;
	public Long NVS;
	public Long DG_FREESPACE;
	public Long N_DISKS;
	public Long N_LUNS;
	public String OS_TYPE;
	public String TYPE;
	public String IP_ADDRESS;
	public String CODE_LEVEL;
	public String SERIAL_NUMBER;
	public String PHYSICAL_PACKAGE_ID;
	public String DEDICATED;
	public String TIME_ZONE;
	public String FIRMWARE_VERSION;
}