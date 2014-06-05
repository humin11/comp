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
import javax.persistence.UniqueConstraint;

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
@Table(name="T_RES_ZONE_MEMBER",uniqueConstraints={@UniqueConstraint(columnNames={"PORT_INDEX","DOMAIN_ID","PORT_WWN","TYPE"})})
public class TResZoneMember {
	@Id
	public String ID;
	public String DOMAIN_ID;
	public String PORT_INDEX;
	public String PORT_WWN;
	public String ALIAS_NAME;
	public Short TYPE;
	public Short VENDOR_TYPE;
	public String DESCRIPTION;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public String OPERATIONAL_STATUS;
}
