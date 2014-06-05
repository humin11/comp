/**
 * File Name：VResHost.java
 *
 * Version：
 * Date：2012-3-1
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models.host;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="v_storage_getInfo",schema="CMDB")
public class VResHost{
	
	@Id
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
