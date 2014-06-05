/**
 * File Name：TResTapeLibrary.java
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
 * Class Name：TResTapeLibrary
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 下午11:27:40
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 下午11:27:40
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_TAPE_LIBRARY",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public class TResTapeLibrary extends ResModel {
	public String PRIMARY_OWNER_NAME;
	public String PRIMARY_OWNER_CONTACT;
	public String ACCESS_INFO;
	public String FIRMWARE_VERSION;
	public Short DETECTABLE;
	public Integer MAX_CARTRIDGES;
	public String SERIAL_NUMBER;
}