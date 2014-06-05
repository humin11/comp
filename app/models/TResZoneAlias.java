/**
 * File Name：TResZoneMember.java
 *
 * Version：
 * Date：2012-3-1
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResZoneMember
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午11:15:24
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午11:15:24
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_ZONE_ALIAS")
public class TResZoneAlias {
	@Id
	public String ID;
	public String NAME;
	public String DESCRIPTION;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
}
