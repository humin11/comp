/**
 * File Name：TResRaidGroup2Disk.java
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
 * Class Name：TResRaidGroup2Disk
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午4:34:55
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午4:34:55
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_RAIDGROUP2DISK",uniqueConstraints={@UniqueConstraint(columnNames={"RAIDGROUP_ID","DISK_ID"})})
public class TResRaidGroup2Disk {
	@Id
	public String ID;
	public String RAIDGROUP_ID;
	public String DISK_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public Short OPERATIONAL_STATUS;
}
