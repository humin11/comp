/**
 * File Name：TResZone2Member.java
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
 * Class Name：TResZone2Member
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午11:21:16
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午11:21:16
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_ZONE2MEMBER",uniqueConstraints={@UniqueConstraint(columnNames={"ZONEMEMBER_ID","ZONE_ID"})})
public class TResZone2Member {
	@Id
	public String ID;
	public String ZONEMEMBER_ID;
	public String ZONE_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
}