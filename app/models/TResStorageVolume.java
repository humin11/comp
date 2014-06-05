/**
 * File Name TResStorageVolume.java
 *
 * Version
 * Date 2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
 package models;

import models.core.ResModel;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * 
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResStorageVolume
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 上午10:18:32
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 上午10:18:32
 * Remarks：
 * @version 
 *
 */
@Entity
@Table(name="T_RES_STORAGE_VOLUME",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResStorageVolume extends ResModel {
	public Long CAPACITY;
	public Short PACKAGE_REUNDANCY;
	public Short DATA_REDUNDANCY;
	public Long SIZE_ALLOCATED;
	public Long BLOCK_SIZE;
	public Long CONSUMABLE_BLOCKS;
	public Short NATIVE_STATUS;
	public Long NUMBER_OF_BLOCKS;
	public Long THROTTLE;
	public Short VDISK_TYPE;
	public String POOL_ID;
	public String LOGICAL_DISK_TYPE;
	public Short MIRROR_COUNT;
	public String IS_SWAP;
	public String REPLICATION_MODE;
	public String NOT_EXPOSED;
	public Long OVERHEAD;
	public String LUN_IDENTITY;
	public Integer LSS;
	public String SERIAL_NUMBER;
	public Short UNDERLYING_REDUNDANCY;
	public Integer DELTA_RESERVATION;
	public Short REDUNDANCY;
	public Long LOGICAL_CAPACITY;
	public Long LOGICAL_FREE;
	public Short FORMAT;
	public Short IS_MAPPING;
	public String FLASH_COPY_ID;
	public String FLASH_COPY_NAME;
	public String METRO_MIRROR_ID;
	public String METRO_MIRROR_NAME;
	public Integer FAST_WRITE_STATE;
	public String PREFERRED_NODE;
	public String MDISK_ID;
	public Short IS_FORMATTED;
	public Short NO_SINGLE_PT_FAILURE;
	public Short VOLUME_NUMBER;
	public Short COPY_RELATIONSHIP;
	public String VPD_PAGE83_DATA;
	public Short IS_SE_VOLUME;
	public Long USED_SPACE;
	public String RAID_LEVEL;
	public Short IS_ENCRYPTED;
	public Short IS_ENCRYPTABLE;

    public static Model.Finder<String, TResStorageVolume> find = new Model.Finder<String, TResStorageVolume>(
            String.class, TResStorageVolume.class
    );

    public static List<TResStorageVolume> findBySubsystemId(String subsystemId){
        return find.where().eq("SUBSYSTEM_ID",subsystemId).findList();
    }

}