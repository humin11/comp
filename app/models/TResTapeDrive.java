/**
 * File Name：TResTapeDrive.java
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
 * Class Name：TResTapeDrive
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 下午11:39:10
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 下午11:39:10
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_TAPE_DRIVE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResTapeDrive extends ResModel {
	public String DEVICE_ID;
	public String NEEDS_CLEANING;
	public Long MOUNT_COUNT;
	public Short AVAILABILITY;
	public Long POWER_ON_HOURS;
	public Long TOTAL_POWER_ON_HOURS;
	public String NODE_ID;
	public String FIRMWARE_VERSION;
	public Short DETECTABLE;
	public String LOCATION_ID;
	public String SERIAL_NUMBER;
}
