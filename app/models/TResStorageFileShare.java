/**
 * File Name：TResStorageFileShare.java
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
 * Class Name：TResStorageFileShare
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午3:06:05
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午3:06:05
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_STORAGE_FILE_SHARE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResStorageFileShare extends ResModel {
	public String SHARING_DIRECTORY;
}
