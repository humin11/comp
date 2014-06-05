/**
 * File Name：TResFabric2Switch.java
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
 * Class Name：TResFabric2Switch
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午10:57:41
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午10:57:41
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_FABRIC2SWITCH",uniqueConstraints={@UniqueConstraint(columnNames={"FABRIC_ID","SWITCH_ID"})})
public class TResFabric2Switch {
	@Id
	public String ID;
	public String FABRIC_ID;
	public String SWITCH_ID;
	public String DOMAIN_ID;
	public String OPERATIOINAL_STATUS;
	public String CONSOLIDATED_STATUS;
	public Short DETECTABLE;
	public Timestamp UPDATE_TIME;
}
