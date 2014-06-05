/**
 * File Name：TAlarm.java
 *
 * Version：
 * Date：2012-3-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：TAlarm
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-29 下午2:29:54
 * Last Modified By：tigaly
 * Last Modified：2012-3-29 下午2:29:54
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_ALARM_FORCE")
public class TAlarmForce{

	@Id
	public String ID;
	
	public String NAME;
}
