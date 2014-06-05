/**
 * File Name：TPrfDsport.java
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
 * Class Name：TPrfDsport
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 上午10:57:20
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 上午10:57:20
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_PRF_DSPORT",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfDsport extends PrfModel{
	public Long READ_IO;
	public Long WRITE_IO;
	public Long READ_KB;
	public Long WRITE_KB;
	public Long READ_TIME;
	public Long WRITE_TIME;
	public Long TOTAL_KB;
	public Long TOTAL_IO;
	public Long TOTAL_TIME;
	
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
	
	public Long CKD_READ_IO;
	public Long CKD_WRITE_IO;
	public Long CKD_READ_KB;
	public Long CKD_WRITE_KB;
	public Long CKD_READ_TIME;
	public Long CKD_WRITE_TIME;

	public Short M_SEND_UTIL_PERC;
	public Short M_RECV_UTIL_PERC;
	public Short M_SEND_BNDW_PERC;
	public Short M_RECV_BNDW_PERC;
	
	public Long LIP_COUNT;
	public Long NOS_COUNT;
	public Long ERROR_FRAMES;
	public Long DUMPED_FRAMES;
	public Long LINK_FAILURES;
	public Long LOSS_OF_SYNC_COUNT;
	public Long LOSS_OF_SIGNAL_COUNT;
	public Long CRC_ERRORS;
	public Long PRIMITIVE_SEQ_PRT_ERR_CNT;
	public Long INVALID_TRANSMISSION_WORDS;
	public Long FRAMES_TOO_SHORT;
	public Long FRAMES_TOO_LONG;
	public Long ADDRESS_ERRORS;
	public Long BUFFER_CREDIT_NOT_PRVD;
	public Long BUFFER_CREDIT_NOT_RCVD;
	public Long DELIMITER_ERRORS;
	public Long OUT_OF_ORDER_DATA_COUNT;
	public Long OUT_OF_ORDER_ACK_COUNT;
	public Long DUPLICATE_FRAME_COUNT;
	public Long INVALID_RELATIVE_OFFSETS;
	public Long SEQ_TIMEOUT_COUNT;
	
	public Long BAD_RX;
	public Long RX_EOF;
	public Long PROTO_ERROR;
	/**
	 * ENC_IN
	 */
	public Long ENCODING_DISPARITY_ERRORS;
	public Long LINK_RESETS_TRANSMITTED;
	public Long LINK_RESETS_RECEIVED;
	public Long CLASS_FRAMES_DISCARDED;
	public Long INVALID_ORDERED_SETS;
	public Long FBSY_FRAMES;
	public Long PBSY_FRAMES;
	public Long FRJT_FRAMES;
	public Long PRJT_FRAMES;
	public Long BB_CREDIT_ZERO;
}
