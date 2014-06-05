/**
 * File Name：TPrfHost.java
 *
 * Version：
 * Date：2012-3-3
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TPrfHost
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:34:08
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:34:08
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_PRF_HOST",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfHost extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
	public Double CPU_USAGE;
	public Long CPU_USAGE_MHZ;
	public Double CPU_UTILIZATION;
	public Double MEM_USAGE;
	public Long USED_MEM;
	public Long FREE_MEM;
	
	public String POWER_STATUS;
	public Long DISK_READ_IO;
	public Long DISK_WRITE_IO;
	public Long DISK_READ_KB;
	public Long DISK_WRITE_KB;
	public Long NET_RECEIVE_KB;
	public Long NET_TRANSMIT_KB;
}
