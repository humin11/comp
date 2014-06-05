/**
 * File Name：TResVendor.java
 *
 * Version：
 * Date：2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResVendor
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午4:47:54
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午4:47:54
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_VENDOR",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public class TResVendor extends Model {
	public String VENDOR_DESCRIPTION;
}
