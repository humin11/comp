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
@Table(name="T_PRF_DSSYSTEM_RC",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfDssystemRC extends PrfModel{
	
	public Long ALL_RIO;

    public Long ALL_WRITE;

    public Long RIO_ERROR;

    public Long INIT_RIO;

    public Long INIT_TRANSFER;

    public Long INIT_TIME;

    public Long UPDATE_COPY_RIO;

    public Long UPDATE_COPY_TRANSFER;

    public Long UPDATE_COPY_TIME;

    public Long PAIR_SYNC;

    public Long OUT_OF_TRACKS;
	
}
