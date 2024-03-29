/**
 * File Name：TResRaidGroup2Vol.java
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
 * Class Name：TResRaidGroup2Vol
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午4:28:14
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午4:28:14
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_VOL2DISK",uniqueConstraints={@UniqueConstraint(columnNames={"DISK_ID","VOLUME_ID"})})
public class TResVol2Disk {
	@Id
	public String ID;
	public String DISK_ID;
	public String VOLUME_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
}
