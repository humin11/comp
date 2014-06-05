/**
 * File Name：TResBateery.java
 *
 * Version：
 * Date：2012-3-1
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
 * Class Name：TResBateery
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午2:30:51
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午2:30:51
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_BATTERY",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResBattery extends ResModel {
	public String DEVICE_ID;
	public String STATUS;
	public String ENABLED_STATE;
	public String PHYSICAL_PACKAGE_ID;
	public String SERIAL_NUMBER;
}
