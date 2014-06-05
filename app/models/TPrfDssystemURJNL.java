/**
 * File Name：TPrfDssystem.java
 *
 * Version：
 * Date：2012-2-29
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
 * Class Name：TPrfDssystem
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午8:33:08
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午8:33:08
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_PRF_DSSYSTEM",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfDssystemURJNL extends PrfModel{

	public Long FB_READ_IO;
	public Long FB_WRITE_IO;
	public Long FB_READ_KB;
	public Long FB_WRITE_KB;
	public Long FB_READ_TIME;
	public Long FB_WRITE_TIME;
	public Long FB_TOTAL_KB;
	public Long FB_TOTAL_IO;
	
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
	public Long BACK_READ_QUEUE_TIME;
	public Long BACK_WRITE_QUEUE_TIME;
	
	public Long READ_NRM_IO;
	public Long WRITE_NRM_IO;
	public Long READ_SEQ_IO;
	public Long WRITE_SEQ_IO;
	public Long READ_NRM_HITS;
	public Long WRITE_NRM_HITS;
	public Long READ_SEQ_HITS;
	public Long WRITE_SEQ_ITS;
	
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_IO;
	public Long READ_CACHE_KB;
	public Long WRITE_CACHE_KB;
	public Long READ_HITS;
	public Long WRITE_HITS;
	public Long READ_HITS_TIME_COUNTER;
	public Long WRITE_HITS_TIME_COUNTER;
	public Long READ_IO_TIME_COUNTER;
	public Long WRITE_IO_TIME_COUNTER;
	
	public Long READ_KB;
	public Long WRITE_KB;
	public Long TOTAL_KB;
	public Long READ_TIME;
	public Long WRITE_TIME; 
	
	public Long D2C_NRM;
	public Long D2C_SEQ;
	public Long C2D;
	public Long DFW_NRM_IO;
	public Long DFW_SEQ_IO;
	public Long DELAY_IO;
	public Long RMR;
	public Long RMR_HITS;
	public Long QWP;
	public Long XRC_XFR_TRKS;
	public Long HPF_READ_IO;
	public Long HPF_WRITE_IO;
	
	public Long EFD_READ_HITS;
	public Long EFD_READ_MISSES;
	public Long EFD_WRITE_HITS;
	
	public Long READ_HISTOGRAM;
	
	public Short M_AVG_HOLD_TIME;
	public Short M_UTIL_PERC;
	
	public Long IDLE_TIME_COUNTER;
	public Long IO_TIME_COUNTER;
	
	/**
	 * For SVC
	 */
	public Long COMM_LOCL_SEND_IO;
	public Long COMM_LOCL_RECV_IO;
	public Long COMM_LOCL_SEND_E_TIME;
	public Long COMM_LOCL_RECV_E_TIME;
	public Long COMM_LOCL_SEND_Q_TIME;
	public Long COMM_LOCL_RECV_Q_TIME;
	public Long COMM_RMOT_SEND_IO;
	public Long COMM_RMOT_RECV_IO;
	public Long COMM_RMOT_SEND_E_TIME;
	public Long COMM_RMOT_RECV_E_TIME;
	public Long COMM_RMOT_SEND_Q_TIME;
	public Long COMM_RMOT_RECV_Q_TIME;
	public Long PORT_HOST_SEND_IO;
	public Long PORT_HOST_RECV_IO;
	public Long PORT_HOST_SEND_KB;
	public Long PORT_HOST_RECV_KB;
	public Long PORT_DISK_SEND_IO;
	public Long PORT_DISK_RECV_IO;
	public Long PORT_DISK_SEND_KB;
	public Long PORT_DISK_RECV_KB;
	public Long PORT_LOCL_SEND_IO;
	public Long PORT_LOCL_RECV_IO;
	public Long PORT_LOCL_SEND_KB;
	public Long PORT_LOCL_RECV_KB;
	public Long PORT_RMOT_SEND_IO;
	public Long PORT_RMOT_RECV_IO;
	public Long PORT_RMOT_SEND_KB;
	public Long PORT_RMOT_RECV_KB;
	
	public Long LINK_FAILURES;
	public Long LOSS_OF_SYNC_COUNT;
	public Long LOSS_OF_SIGNAL_COUNT;
	public Long CRC_ERRORS;
	public Long PRIMITIVE_SEQ_PRT_ERR_CNT;
	public Long INVALID_TRANSMISSION_WORDS;
	public Long ZERO_BB_CREDIT_TIME;
	
	public Integer PEAK_READ_TIME;
	public Integer PEAK_WRITE_TIME;

	public Long QUEUE_ARRIVALS;
	public Long QUEUE_LENGTH;
	public Long QUEUE_LENGTHS_ONARRIVAL;
	public Long PCT_DIRTY_PAGES;
	public Long DIRTY_PAGES;
	public Long WRITE_FLUSHES;
	public Long WRITE_KBYTES_FLUSHES;
	public Long IDLE_WATER_FLUSHES;
	public Long KB_PREFETCHED;
	public Long KB_PREFETCHED_NOT_USED;
	
	public Long SEQ_TIMEOUT_COUNT;
	/*
	 *for panasas 
	 */
	 
	public Double CPU_UTIL;
	public Long OPERATION;
	public Integer REAL_LOCATION;
	public Long READS_COUNT;
	public Long WRITES_COUNT;
	public Long DATA_USED;
	public Long METADATA_USED;
	public Double READ_SPEED;
	public Double WRITE_SPEED;
	public Integer DISK_ACTIVITY;
	public Double RESPONSE_TIME;
	public Double TOTAL_CAPACITY;
	public Double USED_CAPACITY;
	public Double AVAILABLE_CAPACITY;
	public Double RESERVED_CAPACITY;
}
