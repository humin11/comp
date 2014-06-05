
package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_PRF_VM",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfVM extends PrfModel{
	public String POWER_STATUS;
	public Long DISK_READ_IO;
	public Long DISK_WRITE_IO;
	public Long DISK_READ_KB;
	public Long DISK_WRITE_KB;
	public Double CPU_USAGE;
	public Double MEM_USAGE;
	public Long NET_RECEIVE_KB;
	public Long NET_TRANSMIT_KB;
	
}

