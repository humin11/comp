/**
 * File Name：TResZone.java
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
 * Project Name：com.cldouwei.monitor.model.host
 * Class Name：VResHost
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午11:06:28
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午11:06:28
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_CMDB_HOST",uniqueConstraints={@UniqueConstraint(columnNames={"OSITHOSTNAME"})})
public class TCMDBHost{
	@Id
	public Long ID;
	public String SUBSYSTEM_ID;
	public String OSITHOSTNAME;
	public String OSTOIP;
	public String OSFOOLIP;
	public String OSAMMIP;
	public String OSHEARTIP1;
	public String OSHEARTIP2;
	public String APPNAME;
	public String OSNAME;
	public String AREA;
	public String LOCATIONS;
	public String ROOM;
	public String CAGELOCATION;
	public String SETUPLOCATION;
	public String UCOUNTS;
	public String HARDWAREA;
	public String SYSA;
	public String APPA;
	public String NOTE;
}
