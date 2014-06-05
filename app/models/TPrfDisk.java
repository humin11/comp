/**
 * File Name：TPrfDisk.java
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
 * Class Name：TPrfDisk
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 上午11:43:24
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 上午11:43:24
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_PRF_DISK",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfDisk extends PrfModel{
	public Long READ_NRM_IO;
	public Long WRITE_NRM_IO;
	public Long READ_SEQ_IO;
	public Long WRITE_SEQ_IO;
	public Long READ_NRM_HITS;
	public Long WRITE_NRM_HITS;
	public Long READ_SEQ_HITS;
	public Long WRITE_SEQ_HITS;
	
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
	
	public Long D2C_NRM_TRKS;
	public Long D2C_SEQ_TRKS;
	public Long C2D_TRKS;
	public Long NVS_ALLOCS;
	public Long DFW_NRM_IO;
	public Long DFW_SEQ_IO;
	public Long DFW_DELAY_IO;
	public Long CACHE_DELAY_IO;

	public Long RMR_IO;
	public Long RMR_HITS;
	public Long QUICK_WRITE_PROM;
	public Long CFW_READ_IO;
	public Long CFW_WRITE_IO;
	public Long CFW_READ_HITS;
	public Long CFW_WRITE_HITS;
	
	public Long XRC_READ_TRKS;
	public Long XRC_CONTAM_WRITES;
	public Long XRC_XFR_TRKS;
	
	public Long IRREG_TRK_IO;
	public Long IRREG_TRK_HITS;
	
	public Long RANK_READ_IO;
	public Long RANK_WRITE_IO;
	public Long RANK_READ_KB;
	public Long RANK_WRITE_KB;
	public Long RANK_READ_TIME;
	public Long RANK_WRITE_TIME;
	
	public Long ICL_READ_IO;
	public Long BC_WRITE_IO;
	public Long RANK_XFR_TIME;
	public Long HPF_READ_IO;
	public Long HPF_WRITE_IO;
	
	public Short M_DISK_UTIL;
	
	public Long IDLE_TIME_COUNTER;
	public Long IO_TIME_COUNTER;
	
	public Long DELAY_IO;
	public Long EFD_READ_HITS;
	public Long EFD_READ_MISSES;
	public Long EFD_WRITE_HITS;
	
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
	
	public Long READ_HISTOGRAM;
}
