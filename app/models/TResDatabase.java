/**
 * File Name：TResDatabase.java
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
 * Class Name：TResDatabase
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 下午11:11:09
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 下午11:11:09
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_DATABASE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResDatabase extends ResModel {
	public Long RDBMS_ID;
	public String RDBMS_HOST;
	public String RDBMS_NAME;
	public Long GROUP_ID;
	public Long BLOCK_SIZE;
	public String LOG_MODE;
	public String STATUS;
	public Long MAX_INSTANCES;
	public Long TABLE_SPACES;
	public Long MAX_DATAFILES;
	public Long DATAFILES;
	public Long MAX_LOGFILES;
	public Long LOGFILES;
	public Long TOTAL_SIZE;
	public Long CAPACITY;
	public Long FREE_SPACE;
	public Long LOG_SIZE;
	public Long LOG_FREESP;
	public Long LOG_ONLYSIZE;
	public Long MIRROR_SIZE;
	public String DBMS_TYPE;
}