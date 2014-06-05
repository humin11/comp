/**
 * File Name TResHost.java
 *
 * Version
 * Date 2012-2-21
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
 * Project Name com.cloudwei.monitor.server
 * Class Name TResHost
 * Class Desc
 * Author Spring
 * Create Date 2012-2-21 9:30:14
 * Last Modified By Spring
 * Last Modified 2012-2-21 9:30:14
 * Remarks
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_HOST",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public class TResHost extends ResModel {
	public String GUID;
	public String HOST_URL;
	public String OS_PATCH;
	public String OS_TYPE;
	public String OS_VERSION;
	public String OS_DESCRIPTION;
	public String OS_VENDOR;
	public String OS_VENDORNAME;
	public String OS_VENDORCODE;
	public String NETWORK_NAME;
	public String NETWORK_DESCRIPTION;
	public String DOMAIN_NAME;
	public String HARDWARE_ID;
	public String TIME_ZONE;
	public String PRODUCT_STATES;
	public String CPU_ARCHITECTURE;
	public String ORIGINAL_ALIAS;
	public String IP_ADDRESS;
	public String DETECTABLE;
	public String STATUS;
	public Long MEMEORY_SIZE;
	public Long SWAP_SIZE;
	public Long MEMEORY_USAGE;
	public String PARENT_ID;
	public String V_VERSION;
	public Long HOST_COST_MEMORY;
	public Long STORAGE_COST_SIZE;
	public Long STORAGE_USED;
	public String STORAGE_NAME;
	public Long DISK_SIZE;
	public Long DISK_USAGE;
	public String DISK_PATH;
	public Integer ETHERNET_CARD_NUM;
	public Integer CPU_CORES;
	public Integer CORES_PERSOCKET;
	public Integer CPU_MHZ;
	public Integer CPU_USAGE;
	public String USER_ATTRIB4;
}
