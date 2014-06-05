/**
 * File Name：TPrfVg.java
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
 * Class Name：TPrfVg
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 上午1:38:44
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 上午1:38:44
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name = "T_PRF_VG", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"TIME_ID", "ELEMENT_ID" }) })
public class TPrfVg extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
}
