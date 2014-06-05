/**
 * File Name：TResController.java
 *
 * Version：
 * Date：2012-2-29
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
 * Class Name：TResController
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午3:48:38
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午3:48:38
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_BLADE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResBlade extends ResModel {
	public String SERIAL_NUMBER;
	public String DATA_SERIAL;
	
	public String SW_REV;
	public String BIOS_REV;
	public String BIOS_DATE;
	public String CPU_MODEL;
	public String MEM_SIZE;
	public String HW_REV;
	public String PROG_MODEL;
	public String IP_ADDRESS;
	public Integer SLOT;
	public String SET_ID;
	public String TYPE;
	public String SHELF_NAME;
	public String SHELF_ID;
	public String SET_NAME;
}
