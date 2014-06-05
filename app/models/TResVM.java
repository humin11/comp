
package models;

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_VM",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","HARDWARE_ID"})})
public class TResVM extends ResModel {
	public String GUID;
	public String OS_TYPE;
	public String OS_DESCRIPTION;
	public String NETWORK_NAME;
	public String HARDWARE_ID;
	public String TIME_ZONE;
	public String VERSION;
	public String IP_ADDRESS;
	public String MAC;
	public String STATUS;
	public String HOST_ID;
	public String USER_ATTRIB4;
	public Integer MEMEORY_SIZE;
	public Integer MEMEORY_USAGE;
	public Integer HOST_COST_MEMORY;
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
	public Integer UPTIME;
	public String PARENT_ID;
}
