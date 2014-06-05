/**
 * File Name：PrfModel.java
 *
 * Version：
 * Date：2012-3-6
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models.core;

import java.sql.Timestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：PrfModel
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-6 上午11:42:57
 * Last Modified By：tigaly
 * Last Modified：2012-3-6 上午11:42:57
 * Remarks：
 * @version 
 * 
 */

@MappedSuperclass
public class PrfModel {
	@Id
	public String ID;
	public String TIME_ID;
	public String ELEMENT_ID;
	public String ELEMENT_NAME;
	public String ELEMENT_TYPE;
	public String SUBSYSTEM_ID;
	public Integer INTERVAL_LEN;
	public Long DEV_TIME;
	public Long UTILIZATION;
	public Timestamp UPDATE_TIME; 
}
