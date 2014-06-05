package models.changed;

import com.avaje.ebean.annotation.Formula;
import models.core.ChangedModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_STORAGE_SUBSYSTEM_CHANGED",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME"})})
public class TResStorageSubsystemChanged extends ChangedModel {
	public Long RAW_CAPACITY;
	public Long ASSIGNED_CAPACITY;
	public Long UNASSIGNED_CAPACITY;
	public Long ALLOCATED_CAPACITY;
	public Long UNALLOCATED_CAPACITY;
	public Long MAPPING_CAPACITY;
	//@Formula("ALLOCATED_CAPACITY-MAPPING_CAPACITY")
	public Long UNMAPPING_CAPACITY;
	public Long USED_CAPACITY;
	//@Formula("MAPPING_CAPACITY-USED_CAPACITY")
	public Long UNUSED_CAPACITY;
	public Long CACHE;
	public Long NVS;
	public Long DG_FREESPACE;
	public Long N_DISKS;
	public Long N_LUNS;
	public String OS_TYPE;
	public String TYPE;
	public String IP_ADDRESS;
	public String CODE_LEVEL;
	public String SERIAL_NUMBER;
	public String PHYSICAL_PACKAGE_ID;
	public String DEDICATED;
	public String TIME_ZONE;
	public String FIRMWARE_VERSION;
}