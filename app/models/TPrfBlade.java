package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_PRF_BLADE",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfBlade extends PrfModel{
	public Double CPU_UTIL;
	public Long OPERATION;
	public Long NFS_SPEED;
	
	public Integer REAL_LOCATION;
	public Long READS_COUNT;
	public Long WRITES_COUNT;
	public Long DATA_USED;
	public Long METADATA_USED;
	public Double READ_SPEED;
	public Double WRITE_SPEED;
	public Integer DISK_ACTIVITY;
	public Double RESPONSE_TIME;
	public Double TOTAL_CAPACITY;
	public Double USED_CAPACITY;
	public Double AVAILABLE_CAPACITY;
	public Double RESERVED_CAPACITY;

}
