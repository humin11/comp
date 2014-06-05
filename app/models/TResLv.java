/**
 * File Name：TResLv.java
 *
 * Version：
 * Date：2012-3-3
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
 * Class Name：TResLv
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:09:13
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:09:13
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_LV",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResLv extends ResModel {
	public String DISK_TYPE;
	public String FORMAT;
	public Long CAPACITY;
	public Long USED_CAPACITY;
	public Integer TOTAL_PP;
	public Integer FREE_PP;
	public Integer USED_PP;
	public Integer ALLOC_PP;
	public Short MIRROR_COUNT;
	/**
	 * 1 - swap <br>
	 * 0 - not swap
	 */
	public Short IS_SWAP;
	public Short USE_COUNT;
	/**
	 * 1 - VCMDB <br>
	 * 0 - not VCMDB
	 */
	public Short IS_VCMDB;
	public Short REPLICATION_MODE;
	public Short NOT_EXPOSED;
	public String OVERHEAD;
	public String VG_ID;
	public String LUN_IDENTITY;
	public Integer SSRAIDL;
	public Short STORAGE_TYPE;
	public Integer HYPERVISOR_ID;
	public String MOUNT_POINT;
	public String STATUS;
}
