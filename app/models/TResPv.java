/**
 * File Name：TResPv.java
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
 * Class Name：TResPv
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:17:14
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:17:14
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_PV",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResPv extends ResModel {
	public String SERIAL_NUMBER;
	public String FIRMWARE_REV;
	public String MANUFACTURE;
	public Short USE_COUNT;
	public String DEVICE_TYPE;
	public String UNSUPPORTED_MODEL;
	public Short FLAGS;
	public String RAWDEVICE_ID;
	public String VG_ID;
	public String VOLUME_ID;
	public String TAG;
	public Long CAPACITY;
	public Long USED_CAPACITY;

	public Integer TOTAL_PP;
	public Integer USED_PP;
	public Integer FREE_PP;
	public String DISK_TYPE;
	public String SPEED;
	
	public String RAID_LEVEL;
	
	/**
	 * For MultiPath IO
	 */
	public String PARENT_PATH_ID;
	public String PARENT_PATH_NAME;
	/**
	 * 1 - REMOTE <br>
	 * 0 - not REMOTE
	 */
	public Short IS_REMOTE;
}
