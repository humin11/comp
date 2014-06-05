/**
 * File Name：TResStorageFileSystem.java
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
 * Class Name：TResStorageFileSystem
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午2:45:24
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午2:45:24
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_STORAGE_FILE_SYSTEM",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResStorageFileSystem extends ResModel {
	public Integer MAX_FILES;
	public Integer USED_INODES;
	public Integer FREE_INODES;
	public Long CAPACITY;
	public Long USED_SPACE;
	public Long FREE_SPACE;
	public Integer FILE_COUNT;
	public Integer DIRECTORY_COUNT;
	public Integer USE_COUNT;
	public Integer REMOVE_MOUNT;
	public String MOUNT_POINT;
	public String ENCRYPTION_METHOD;
	public String COMPRESSION_METHOD;
	public String EXPORT_NAME;
	public String FILESYSTEM_TYPE;
	public String READ_ONLY;
	/**
	 * Name of the Computer System that hosts the filesystem of this File. <br />
	 * SMI-S: CSName <br />
	 */
	public String CS_NAME;
	public String VOLUME_ID;
	public String SYSTEM_TYPE;
	public Integer ISFIXED_SIZE;
	public Integer BLOCK_SIZE;
	public String CASE_PRESERVED;
	public String CASE_SENSITIVE;
	public Integer CLUSTER_SIZE;
	public Integer RESIZE_INCREMENT;
	public Integer MAXFILENAME_LENGTH;
	public Integer PERSISTENCE_TYPE;
	public Integer LA_DEFINITION_REQUIRED;
	public String CODE_SET;
}
