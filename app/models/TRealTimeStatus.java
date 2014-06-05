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
@Table(name = "T_REALTIME_STATUS")
public class TRealTimeStatus {
	@Id
	public String ID;
	public String SUBSYSTEM_ID;
	public String OPERATIONAL_STATUS;
	public String HEALTH_STATUS;
	public String STATUS;
	public String TYPE;
	public Timestamp UPDATE_TIME;
}
