/**
 * File Name：TPrfFan.java
 *
 * Version：
 * Date：2012-3-1
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TPrfFan
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午12:03:15
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午12:03:15
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_PRF_FAN",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfFan extends PrfModel{
	public Double SPEED;
	public Double TEMPERATURE;
}
