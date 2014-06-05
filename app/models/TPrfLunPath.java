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

@Entity
@Table(name = "T_PRF_LunPath", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"TIME_ID", "ELEMENT_ID" }) })
public class TPrfLunPath extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
	public Double AVG_TIME;
	public Double BUSY;
}
