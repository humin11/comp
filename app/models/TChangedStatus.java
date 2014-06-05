/**
 * File Name：TRealTimeStatus.java
 *
 * Version：
 * Date：2013-1-17
 * Copyright CloudWei Dev Team 2013 
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
 * Project Name：com.cloudwei.monitor.model Class Name：TRealTimeStatus Class
 * Desc： Author：tigaly Create Date：2013-1-17 下午4:12:22 Last Modified By：tigaly
 * Last Modified：2013-1-17 下午4:12:22 Remarks：
 * 
 * @version
 * 
 */

@Entity
@Table(name="T_CHANGED_STATUS",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TChangedStatus {
	@Id
	public String ID;
	public String ELEMENT_ID;
	public String SUBSYSTEM_ID;
	public String OPERATIONAL_STATUS;
	public String HEALTH_STATUS;
	public String STATUS;
	public String TYPE;
	public String CHANGED_LOG;
	public String CHANGED_TYPE;
	public Timestamp CHANGED_TIME;
}
