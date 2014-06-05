/**
 * File Name：TResPort2Port.java
 *
 * Version：
 * Date：2012-2-29
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
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResPort2Port
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午4:09:19
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午4:09:19
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_CHANGED_PORT2PORT",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TChangedPort2Port {
	@Id
	public String ID;
	/**
	 * Switch port
	 */
	public String PORT_ID1;
	/**
	 * Switch target port
	 */
	public String PORT_ID2;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
	public String SUBSYSTEM_ID;
	public Timestamp CHANGED_TIME;
	public Short CHANGED_TYPE;
	public String CHANGED_LOG;
	public String ELEMENT_ID;
}
