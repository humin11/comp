/**
 * File Name：TResVg.java
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
 * Class Name：TResVg
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:03:39
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:03:39
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_VG",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResVg extends ResModel {
	public Integer BLOCK_SIZE;
	public Integer NUM_BLOCK;
	public Long CAPACITY;
	public Long USED_CAPACITY;
	public Integer PP_SIZE;
	public Integer TOTAL_PP;
	public Integer ALLOC_PP;
	public String VG_TYPE;
	public String CONFIG;
	public String STATUS;
	public String FORMAT;
	public String RAID_ID;
}
