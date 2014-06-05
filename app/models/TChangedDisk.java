/**
 * File Name：TResDisk.java
 *
 * Version：
 * Date：2012-2-29
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
 * Class Name：TResDisk
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 上午11:32:13
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 上午11:32:13
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_CHANGED_DISK",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TChangedDisk extends ChangedModel {
	public String PHYSICAL_PACKAGE_ID;
	public String FIRMWARE_VERSION;
	public String SERIAL_NUMBER;
	public String SLOT;
	public String RAID_LEVEL;
	public Integer IS_HOTSWAP;
	public Long USED_CAPACITY;
	public Integer SPEED;
	public Long RAW_CAPACITY;
	public Long CAPACITY;
	
	public String STATUS_KEY;
}
