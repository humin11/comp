/**
 * File Name：TResSwitchBlade.java
 *
 * Version：
 * Date：2012-3-2
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
 * Class Name：TResSwitchBlade
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-2 上午12:22:40
 * Last Modified By：tigaly
 * Last Modified：2012-3-2 上午12:22:40
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_SWITCH_BLADE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResSwitchBlade extends ResModel {
	public String SERIAL_NUMBER;
	public String SLOT;
	public String SWITCH_BLADE_TYPE;
	public String FIRMWARE_REVISION;
	public String START_INDEX;
	public String PHYSICAL_PACKAGE_ID;
	public Integer PORT_NUM;
}
