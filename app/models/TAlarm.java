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

import java.sql.Timestamp;

import javax.persistence.Column;
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
@Table(name="T_ALARM")
public class TAlarm{

	@Id
	public String ID;
	
	@Column(length=2000)
	public String ORIGINAL_MESSAGE;
	
	public String DEVICE_ID;
	/**
	 * Send by device.
	 */
	public String ORIGINAL_NAME;
	/**
	 * Display Name
	 */
	public String DEVICE_NAME;
	
	/**
	 * Component ID
	 */
	public String SUBDEVICE_ID;
	public String SUBDEVICE_NAME;
	
	/**
	 * Storage, Switch etc.
	 */
	public String DEVICE_TYPE;
	
	public String SUBDEVICE_TYPE;
	
	public String DEVICE_MODEL;
	
	/**
	 * Performance, Status, Hardware etc.
	 */
	public String ALARM_TYPE;
	
	public String MANUFACTURER;
	
	/**
	 * 0 - Clear <br />
	 * 1 - Information <br />
	 * 2 - Warning <br />
	 * 3 - Minor <br />
	 * 4 - Major <br />
	 * 5 - Critical <br />
	 * 6 - Fatal <br />
	 */
	public Integer ALARM_SEVERITY;
	/**
	 * Original severity of alarm severity
	 */
	public Integer ORIGINAL_SEVERITY;
	public String DEVICE_ORIGINAL_SEVERITY;

	
	public String ALARM_CODE;
	/**
	 * Description digest.
	 */
	public String ALARM_MESSAGE;
	public String ALARM_DESCRIPTION;
	public String ALARM_ACTION;
	
	public String ALARM_STATUS;
	public String ALARM_LOCATION;
	
	public Timestamp FIRST_OCCURENCE;
	public Timestamp LAST_OCCURENCE;
	/**
	 * Repeat count
	 */
	public Integer ALARM_COUNT;
	
	public String ATTR1;
	public String ATTR2;
	public String ATTR3;
	public String ATTR4;
	public String ATTR5;
}
