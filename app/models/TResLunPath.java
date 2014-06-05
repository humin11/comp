/**
 * File Name：TResLunMapping.java
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
 * Class Name：TResLunMapping
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:45:59
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:45:59
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_LUN_PATH",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResLunPath extends ResModel {
	public String WWID;
	public String DISK_ID;
	public String VOLUME_ID;
}
