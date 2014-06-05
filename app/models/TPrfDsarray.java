/**
 * File Name：TRrfDsarray.java
 *
 * Version：
 * Date：2012-3-1
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
 * Class Name：TRrfDsarray
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 上午11:32:17
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 上午11:32:17
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_PRF_DSARRAY",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfDsarray extends PrfModel{
	
	public Long PPRC_SEND_IO;
	public Long PPRC_RECV_IO;
	public Long PPRC_SEND_KB;
	public Long PPRC_RECV_KB;
	public Long PPRC_SEND_TIME;
	public Long PPRC_RECV_TIME;
	
	public Long BACK_READ_IO;
	public Long BACK_WRITE_IO;
	public Long BACK_READ_KB;
	public Long BACK_WRITE_KB;
	public Long BACK_READ_TIME;
	public Long BACK_WRITE_TIME;
	
	public Long FRNT_READ_NRM_IO;
	public Long FRNT_WRITE_NRM_IO;
	public Long FRNT_READ_SEQ_IO;
	public Long FRNT_WRITE_SEQ_IO;
	public Long FRNT_READ_NRM_HITS;
	public Long FRNT_WRITE_NRM_HITS;
	public Long FRNT_READ_SEQ_HITS;
	public Long FRNT_WRITE_SEQ_HITS;
	
	public Long FRNT_READ_KB;
	public Long FRNT_WRITE_KB;
	public Long FRNT_READ_TIME;
	public Long FRNT_WRITE_TIME;
	
	public Long FRNT_D2C_NRM;
	public Long FRNT_D2C_SEQ;
	public Long FRNT_C2D;
	public Long FRNT_DFW_NRM_IO;
	public Long FRNT_DFW_SEQ_IO;
	public Long FRNT_DELAY_IO;
	public Long FRNT_RMR;
	public Long FRNT_RMR_HITS;
	public Long FRNT_QWP;
	public Long FRNT_XRC_XFR_TRKS;
	public Long FRNT_HPF_READ_IO;
	public Long FRNT_HPF_WRITE_IO;
	
	public Short M_UTIL_PERC;
	
	public Long READ_KB;
	public Long WRITE_KB;
	public Long TOTAL_KB;
	public Long READ_TIME;
	public Long WRITE_TIME;
	
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_IO;
	public Long READ_HITS;
	public Long WRITE_HITS;
	public Long READ_HITS_TIME_COUNTER;
	public Long WRITE_HITS_TIME_COUNTER;
	public Long READ_IO_TIME_COUNTER;
	public Long WRITE_IO_TIME_COUNTER;
	public Long IDLE_TIME_COUNTER;
	public Long IO_TIME_COUNTER;
}
