/**
 * File Name：TPrfLv.java
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
 * Class Name：TPrfLv
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:41:20
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:41:20
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name = "T_PRF_LV", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"TIME_ID", "ELEMENT_ID" }) })
public class TPrfLv extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
	public Double AVG_TIME;
	public Double BUSY;
	public Long USED_CAPACITY;
}
