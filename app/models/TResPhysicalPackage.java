/**
 * File Name：TResPhysicalPackage.java
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
 * Class Name：TResPhysicalPackage
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午3:13:59
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午3:13:59
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_PHYSICAL_PACKAGE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","MODEL_ID","VENDOR_ID"})})
public class TResPhysicalPackage extends ResModel {
	public Integer REMOVAL_CONDITIONS;
	public Short REMOVABLE;
	public Short REPLACEABLE;
	public Short HOT_SWAPPABLE;
	public Integer SPEED;
	public Integer RAW_CAPACITY;
	public Integer CAPACITY;
	public String MANUFACTURER;
	public String DEVICE_TYPE;
	public Double HEIGHT;
	public Double DEPTH;
	public Double WIDTH;
	public Double WEIGHT;
	public String PART_NUMBER;
	public String OTHER_IDENTIFYING_INFO;
	public Short POWERED_ON;
	public String LOCATION;
	public String FRU;
	public String PACKAGE_TYPE;
	public String SERIAL_NUMBER;
	public String FIRMWARE_VERSION;
}
