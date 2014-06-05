/**
 * File Name：TResSwitch2Port.java
 *
 * Version：
 * Date：2012-3-12
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：TResSwitch2Port
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-12 下午11:53:51
 * Last Modified By：tigaly
 * Last Modified：2012-3-12 下午11:53:51
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_SWITCH2PORT",uniqueConstraints={@UniqueConstraint(columnNames={"SWITCH_ID","PORT_ID"})})
public class TResSwitch2Port {
	@Id
	public String ID;
	public String SWITCH_ID;
	public String PORT_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
}
