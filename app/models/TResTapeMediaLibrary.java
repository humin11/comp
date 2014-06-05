/**
 * File Name：TResTapeMediaLibrary.java
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
 * Class Name：TResTapeMediaLibrary
 * Class Desc：
 * Author：Administrator
 * Create Date：2012-3-3 下午11:47:47
 * Last Modified By：Administrator
 * Last Modified：2012-3-3 下午11:47:47
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_TAPE_MEDIA_LIBRARY",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResTapeMediaLibrary extends ResModel {
	public String DEVICE_ID;
	public Short MEDIA_FLIP_SUPPORTED;
	public String CAPTION;
	public Integer NODE_ID;
	public String FIRMWARE_VERSION;
	public String LOCATION_ID;
}
