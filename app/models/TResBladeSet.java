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
@Table(name="T_RES_BLADE_SET",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResBladeSet extends ResModel {
	
	public String RAID;
	public Integer REQUEST_SPARES;
	public Integer AVAILABLE_SPARES;
	public Double TOTAL_GB;
	public Double USABLE_GB;
	public Double UNUSABLE_GB;
	public String PROG_MODEL;
}
