/**
 * File Name：TResFilesystem.java
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
 * Class Name：TResFilesystem
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午12:55:52
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午12:55:52
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_FILESYSTEM_CAPACITY",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","ELEMENT_ID","UPDATE_TIME","MOUNT_POINT"})})
public class TResFilesystemCapacity extends ResModel {
	public String LV_ID;
	public Integer MAX_FILES;
	public Integer USED_INODES;
	public Integer FREE_INODES;
	public Integer PHYSICAL_SIZE;
	public Long CAPACITY;
	public Long USED_SPACE;
	public Long FREE_SPACE;
	public Long FILE_COUNT;
	public Integer DIRECTORY_COUNT;
	public String FILESYSTEM_TYPE;
	public Short USE_COUNT;
	public String MOUNT_POINT;
	public Short REMOTE_MOUNT;
	public String ELEMENT_ID;
}
