/**
 * File Name：TPrfTimestamp.java
 *
 * Version：
 * Date：2012-3-3
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TPrfTimestamp
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:57:26
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:57:26
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_PRF_TIMESTAMP",uniqueConstraints={@UniqueConstraint(columnNames={"START_DATE_DEV","START_TIME_DEV","START_DATE_SRV","START_TIME_SRV","INTERVAL_LEN"})})
public class TPrfTimestamp {
	@Id
	public String ID;
	public Integer INTERVAL_LEN;
	public Integer START_DATE_UTC;
	public Integer START_TIME_UTC;
	public Integer START_DATE_DEV;
	public Integer START_TIME_DEV;
	public Integer START_DATE_SRV;
	public Integer START_TIME_SRV;
}

	
