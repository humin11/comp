/**
 * File Name：TPrfNetwork.java
 *
 * Version：
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
 * Class Name：TPrfNetwork
 * Class Desc：
 * Author：tigaly
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name = "T_PRF_NETWORK", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"TIME_ID", "ELEMENT_ID" }) })
public class TPrfNetwork extends PrfModel{
	public Long TOTAL_IO;
	public Long READ_IO;
	public Long WRITE_IO;
	public Long TOTAL_KB;
	public Long READ_KB;
	public Long WRITE_KB;
	public Double AVG_TIME;
	public Double BUSY;
	
	public Long RX_BYTE;
	public Long TX_BYTE;
	public Long RX_PACKET;
	public Long TX_PACKET;
}
