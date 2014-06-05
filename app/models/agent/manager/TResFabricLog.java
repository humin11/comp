package models.agent.manager;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_FABRIC_LOG", uniqueConstraints={@UniqueConstraint(columnNames={"IP"})})
public class TResFabricLog {
	@Id
	public String ID;
	public String NAME;
	public String LAST_TIME;
	public String IP;
}
