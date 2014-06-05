package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_PRF_CPU_CORE",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfCpuCore extends PrfModel{
	public Double USED_PERCENT;
	public Double CORE_UTILIZATION;

}
