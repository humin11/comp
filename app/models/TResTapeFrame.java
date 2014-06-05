/**
 * File Name：TResTapeFrame.java
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
 * Class Name：TResTapeFrame
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 下午11:34:40
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 下午11:34:40
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_TAPE_FRAME",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResTapeFrame extends ResModel {
	public Short LOCK_PRESENT;
	public Short SECURITY_BREACH;
	public Short IS_LOCKED;
	public String SERIAL_NUMBER;
}
