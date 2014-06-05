/**
 * File Name：TResModel.java
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
 * Class Name：TResModel
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午4:42:27
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午4:42:27
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_MODEL",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public class TResModel extends Model {
	public String MODEL_DESCRIPTION;
}