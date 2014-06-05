/**
 * File Name：TResZset2Zone.java
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
 * Class Name：TResZset2Zone
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午11:33:38
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午11:33:38
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_ZSET2ZONE",uniqueConstraints={@UniqueConstraint(columnNames={"ZONE_ID","ZSET_ID"})})
public class TResZset2Zone {
	@Id
	public String ID;
	public String ZONE_ID;
	public String ZSET_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
}
