/**
 * File Name：TResStorageNetworkPort.java
 *
 * Version：
 * Date：2012-3-1
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResStorageNetworkPort
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 下午2:37:34
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 下午2:37:34
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_STORAGE_NETWORK_PORT",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResStorageNetworkPort extends ResModel {
	public String MAC;
	public String IP_ADDRESS;
	public String NETMASK;
	public String BROADCAST;
	public String TYPE;
	public Integer METRIC;
	public Integer MTU;
	public String DESTINATION;
}
