/**
 * File Name：TPrfSwitch.java
 *
 * Version：
 * Date：2012-3-2
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
 * Class Name：TPrfSwitch
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-2 上午12:54:54
 * Last Modified By：tigaly
 * Last Modified：2012-3-2 上午12:54:54
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_PRF_SWITCH",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfSwitch extends PrfModel{
	public Long SEND_KB;
	public Long RECV_KB;
	public Long SEND_PKTS;
	public Long RECV_PKTS;
	public Long PEAK_TX_RATE;
	public Long PEAK_RX_RATE;
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
