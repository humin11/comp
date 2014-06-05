/**
 * File Name：TResTapeMediaLocation.java
 *
 * Version：
 * Date：2012-3-4
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
 * Class Name：TResTapeMediaLocation
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-4 上午12:00:07
 * Last Modified By：tigaly
 * Last Modified：2012-3-4 上午12:00:07
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_TAPE_MEDIA_LOCATION",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResTapeMediaLocation extends ResModel {
	public String LOCATION_TYPE;
	public String LOCATION_COORDINATES;
	public Integer MEDIA_CAPACITY;
	public Short MEDIATYPESSUPPORTED;
}
