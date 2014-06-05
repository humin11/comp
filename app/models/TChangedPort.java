/**
 * File Name：TResPort.java
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
 * Class Name：TResPort
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午2:53:22
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午2:53:22
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_CHANGED_PORT",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TChangedPort extends ChangedModel {
	public Short SLOT_NUMBER;
	public String PORT_NUMBER;
	public String PORT_INDEX;
	public Long PORT_SPEED;
	public Long MAX_SPEED;
	public String PORT_TYPE;
	public String FUNCTION_TYPE;
	public String DEVICE_TYPE;
	public Integer NODE_ID;
	public Short DISPLAY_ICON_TYPE;
	public Integer RIO_LOOP_ID;
	public Short DETECTABLE;
	public String CONTROLLER_ID;
	public String HBA_ID;
	public String SWITCHBLADE_ID;
	public String LOCATION_ID;
	public String PHYSICAL_STATE;
	public String PHYSICAL_STATUS;
	public Short ENABLED_STATE;
	public Short USAGE_RESTRICTION;
	public Short ACTIVE_FC4TYPES;
	public String IS_LICENSED;
	public String IS_VIRTUAL;
	public String STATUS;
}
