package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_PRF_CPU",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfCpu extends PrfModel{
	public Double USED_PERCENT;
	public Long USED_MHZ;
	public Double CPU_IDLE;
	public Double CPU_WAIT;
	public Double CPU_READY;
	public Double CPU_USED;
	public Double CPU_USER;
	public Double CPU_SYS;
	
}
