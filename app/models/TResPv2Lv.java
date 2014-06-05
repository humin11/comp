/**
 * File Name：TResPv2Lv.java
 *
 * Version：
 * Date：2012-3-3
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
 * Class Name：TResPv2Lv
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:27:55
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:27:55
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_PV2LV",uniqueConstraints={@UniqueConstraint(columnNames={"LV_ID","PV_ID"})})
public class TResPv2Lv {
	@Id
	public String ID;
	public String PV_ID;
	public String LV_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public Short OPERATIONAL_STATUS;
}
