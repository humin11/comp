/**
 * File Name：TAlarmOid.java
 *
 * Version：
 * Date：2012-3-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：TAlarmOid
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-29 下午2:38:48
 * Last Modified By：tigaly
 * Last Modified：2012-3-29 下午2:38:48
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_ALARM_OID_RULER", uniqueConstraints={@UniqueConstraint(columnNames={"OID","NAME"})})
public class TAlarmOidRuler {
	@Id
	public String ID;
	public String OID;
	public String NAME;
	public String MIB_NAME;
	public String MIB_VERSION;
	public String DESCRIPTION;
	public String ATTR1;
	public String ATTR2;
	public String ATTR3;
	public String ENUM;
	public String ADDITIONAL;
	public String DEVICE_TYPE;
	public String KEY_RULER;
	public String SCRIPT;
}
