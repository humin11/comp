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

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="T_RES_EXPORT_FILE_SYSTEM",uniqueConstraints={@UniqueConstraint(columnNames={"HOST_IP","FS_NAME","SUB_NAME"})})
public class TResExportFS {
	@Id
	public String ID;
	public String HOST_IP;
	public String FS_NAME;
	public String SUB_NAME;
	public Short ROOT;
	public Short RW;
}
