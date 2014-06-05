/**
 * File Name：TResCache.java
 *
 * Version：
 * Date：2012-3-1
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ResModel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResCache
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午12:31:21
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午12:31:21
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_CACHE",uniqueConstraints={@UniqueConstraint(columnNames={"DEVICE_ID","SUBSYSTEM_ID"})})
public class TResCache extends ResModel {
	public Long STARING_ADDRESS;
	public Long ENDING_ADDRESS;
	public String ERROR_INFO;
	public Timestamp ERROR_TIME;
	public Integer ERROR_ACCESS;
	public Integer ERROR_ADDRESS;
	public Integer CACHE_SIZE;
	public String TYPE;
	public String CACHE_LEVEL;
	public String WRITE_POLICY;
	public String READ_POLICY;
	public String CACHE_TYPE;
	public String LINE_SIZE;
	public String REPLACEMENT_POLICY;
	public Integer FLUSHTIMER;
	public String ASSOCIATIVITY;
	public String DEVICE_ID;
}
