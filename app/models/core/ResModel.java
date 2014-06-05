/**
 * File Name：ResModel.java
 *
 * Version：
 * Date：2012-3-6
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models.core;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;


/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：Res
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-6 上午11:24:46
 * Last Modified By：tigaly
 * Last Modified：2012-3-6 上午11:24:46
 * Remarks：
 * @version 
 * 
 */
@MappedSuperclass
public class ResModel extends Model{
	public String SUBSYSTEM_ID;
	public String ELEMENT_NAME;
	public String DISPLAY_NAME;
	public String DESCRIPTION;
	public String VENDOR_ID;
	public String MODEL_ID;
	public String OPERATIONAL_STATUS;
	public String CONSOLIDATED_STATUS;
	public String PROPAGATED_STATUS;
	public String HEALTH_STATUS;
	public String USER_ATTRIB1;
	public String USER_ATTRIB2;
	public String USER_ATTRIB3;
	public Timestamp DISCOVERED_TIME; 
	public Timestamp UPDATE_TIME; 
	/**
	 * The time of something changed(E.g. Capicity changed,Status changed) 
	 */
	public Timestamp CHANGED_TIME;
}
