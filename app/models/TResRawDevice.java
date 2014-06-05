/**
 * File Name：TResRawDevice.java
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
 * Class Name：TResRawDevice
 * Class Desc：
 * Author：Administrator
 * Create Date：2012-3-3 上午1:24:16
 * Last Modified By：Administrator
 * Last Modified：2012-3-3 上午1:24:16
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_RAW_DEVICE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResRawDevice extends ResModel {
	public String SERIAL_NUMBER;
	public String FIRMWARE_REV;
	public String MANUFACTURE;
	public Short USE_COUNT;
	public String DEVICE_TYPE;
	public String UNSUPPORTED_MODEL;
	public Short FLAGS;
	public String VOLUME_ID;
	public String TAG;
	public Long CAPACITY;
	public String DISK_TYPE;
	public String SPEED;
	/**
	 * 1 - REMOTE <br>
	 * 0 - not REMOTE
	 */
	public Short IS_REMOTE;
}
