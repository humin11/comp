/**
 * File Name：TResFabric.java
 *
 * Version：
 * Date：2012-3-1
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
 * Class Name：TResFabric
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午9:27:36
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午9:27:36
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_FABRIC",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public class TResFabric extends ResModel {
	public String FABRIC_WWN;
	public String SAN_WWN;
	public Short SUPPORTS_ZONING;
	public String PARENT_FABRIC_WWN;
	/**
	 * 1 - Physical Device<br>
	 * 0 - Virtual Device
	 */
	public Short IS_PHYSICAL;
	/**
	 * 1 - true<br>
	 * 0 - false
	 */
	public Short ACTIVE;
	public Long ACT_ZONEDB_CHECKSUM;
	public Long DEF_ZONEDB_CHECKSUM;
	public Short FABRIC_MODE;
	
}
