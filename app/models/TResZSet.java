/**
 * File Name：TResZSet.java
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
 * Class Name：TResZSet
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午11:28:08
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午11:28:08
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_ZSET",uniqueConstraints={@UniqueConstraint(columnNames={"FABRIC_ID","NAME","ACTIVE"})})
public class TResZSet {
	@Id
	public String ID;
	public String FABRIC_ID;
	/**
	 * 1 - true
	 * 2 - false
	 */
	public Short ACTIVE;
	public String DESCRIPTION;
	public String NAME;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
}
