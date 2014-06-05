/**
 * File Name：TPrfHbaPort.java
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
 * Project Name：com.cldouwei.monitor.model Class Name：TPrfHbaPort Class Desc：
 * Author：tigaly Create Date：2012-3-3 上午1:36:49 Last Modified
 * By：tigaly Last Modified：2012-3-3 上午1:36:49 Remarks：
 * 
 * @version
 * 
 */
@Entity
@Table(name = "T_PRF_HBA_PORT", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"TIME_ID", "ELEMENT_ID" }) })
public class TPrfHbaPort extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
	public Long LINK_FAILURES;
	public Long LOSS_OF_SYNC_COUNT;
	public Long LOSS_OF_SIGNAL_COUNT;
	public Long CRC_ERRORS;
	public Long ERROR_FRAMES;
	public Long DUMPED_FRAMES;
	public Long INVALID_TRANSMISSION_WORDS;
}
