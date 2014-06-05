/**
 * File Name：TResHba.java
 *
 * Version：
 * Date：2012-3-3
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResHba
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午12:36:12
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午12:36:12
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_HBA",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResHba extends ResModel {
	public String API_VERSION;
	public String SERIAL_NUMBER;
	public String DRIVER_VERSION;
	public String DRIVER_NAME;
	public String ROM_VERSION;
	public String HW_VERSION;
	public String FIRMWARE_VERSION;
	public String LPAR_ID;
	public String PORT_TYPE;
	public String ISCSI_NAME;
	/**
	 * 2012-09-06
	 */
	public String HBA_TYPE;
}
