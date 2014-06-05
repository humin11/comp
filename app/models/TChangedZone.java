/**
 * File Name：TResZone.java
 *
 * Version：
 * Date：2012-3-1
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
 * Class Name：TResZone
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午11:06:28
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午11:06:28
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_CHANGED_ZONE",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TChangedZone{
	@Id
	public String ID;
	public String FABRIC_ID;
	public String ZONESET_ID;
	/**
	 * 1 - port zone<br>
	 * 2 - wwn zone
	 */
	public Short ZONE_TYPE;
	/**
	 * 1 - true<br>
	 * 2 - false
	 */
	public Short ACTIVE;
	public String NAME;
	public String ALIAS_NAME;
	public Short ZONE_SUBTYPE;
	public Short VENDOR_TYPE;
	public Short VENDOR_SUBTYPE;
	public String DESCRIPTION;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
	public Timestamp CHANGED_TIME;
	public Short CHANGED_TYPE;
	public String CHANGED_LOG;
	public String ELEMENT_ID;
}
