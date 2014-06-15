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
@Table(name="T_PRF_DSSYSTEM_UR",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfDssystemUR extends PrfModel{

    public Long WRITE_RECORD;

    public Long WRITE_TRANSFER;

    public Long INIT_HIT;

    public Long INIT_TRANSFER;

    public Long MJNL_RIO;

    public Long MJNL_JOURNAL;

    public Long MJNL_TRANSFER;

    public Long MJNL_TIME;

    public Long RJNL_RIO;

    public Long RJNL_JOURNAL;

    public Long RJNL_TRANSFER;

    public Long RJNL_TIME;
}
