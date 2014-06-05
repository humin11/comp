/**
 * File Name：TPrfPv.java
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
 * Class Name：TPrfPv
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:42:51
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:42:51
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name = "T_PRF_PV", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"TIME_ID", "ELEMENT_ID" }) })
public class TPrfPv extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
	public Double AVG_TIME;
	public Double BUSY;
}
