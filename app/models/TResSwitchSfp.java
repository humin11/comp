/**
 * File Name：TResSwitchSfp.java
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
 * Class Name：TResSwitchSfp
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-2 上午12:45:28
 * Last Modified By：tigaly
 * Last Modified：2012-3-2 上午12:45:28
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_SWITCH_SFP",uniqueConstraints={@UniqueConstraint(columnNames={"PORT_ID","SUBSYSTEM_ID"})})
public class TResSwitchSfp extends ResModel {
	public String SERIAL_NUMBER;
	public String PORT_ID;
	public String SPEED;
}
