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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.core.ResModel;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResStorageSubsystem
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-28 下午4:59:14
 * Last Modified By：tigaly
 * Last Modified：2012-2-28 下午4:59:14
 * Remarks：
 */
@Entity
@Table(name = "T_RES_STORAGE_SUBSYSTEM", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class TResStorageSubsystem extends ResModel {
    public Long RAW_CAPACITY;
    public Long ASSIGNED_CAPACITY;
    //	@Formula("RAW_CAPACITY-ASSIGNED_CAPACITY")
    public Long UNASSIGNED_CAPACITY;
    public Long ALLOCATED_CAPACITY;
    //	@Formula("ASSIGNED_CAPACITY-ALLOCATED_CAPACITY")
    public Long UNALLOCATED_CAPACITY;
    //	@Formula("(select sum(lun.CAPACITY) from T_RES_STORAGE_VOLUME lun where lun.SUBSYSTEM_ID=ID and lun.IS_MAPPING = 1)")
    public Long MAPPING_CAPACITY;
    //@Formula("ALLOCATED_CAPACITY-MAPPING_CAPACITY")
    public Long UNMAPPING_CAPACITY;
    public Long USED_CAPACITY;
    //@Formula("MAPPING_CAPACITY-USED_CAPACITY")
    public Long UNUSED_CAPACITY;
    //	@Formula("(select sum(cache.CACHE_SIZE) from T_RES_CACHE cache where cache.SUBSYSTEM_ID=ID)")
    public Long CACHE;
    public Long NVS;
    public Long DG_FREESPACE;
    //@Formula("(select count(*) from T_RES_DISK disk where disk.SUBSYSTEM_ID=ID)")
    public Long N_DISKS;
    //@Formula("(select count(*) from T_RES_STORAGE_VOLUME lun where lun.SUBSYSTEM_ID=ID)")
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

    public static Model.Finder<String, TResStorageSubsystem> find = new Model.Finder<String, TResStorageSubsystem>(
            String.class, TResStorageSubsystem.class
    );

    public static List<TResStorageSubsystem> findAll() { return find.all(); }

    public static List<TResStorageSubsystem> find(String hql) {
        return find.where(hql).findList();
    }

    public static TResStorageSubsystem findById(String id) {
        return find.byId(id);
    }

}