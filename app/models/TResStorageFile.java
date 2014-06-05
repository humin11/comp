/**
 * File Name：TResStorageFile.java
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
 * Class Name：TResStorageFile
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午3:03:00
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午3:03:00
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_STORAGE_FILE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResStorageFile extends ResModel {
	public String FS_NAME;
	public String FILESYSTEM_ID;
	/**
	 * Name of the Computer System that hosts the filesystem of this File. <br />
	 * SMI-S: CSName <br />
	 */
	public String CS_NAME;
}
