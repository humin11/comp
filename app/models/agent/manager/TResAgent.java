package models.agent.manager;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_AGENT", uniqueConstraints={@UniqueConstraint(columnNames={"COMPUTER_ID", "NAME","AGENT_HTTP_PORT"})})
public class TResAgent {
	@Id
	public String ID;
	public String COMPUTER_ID;
	public String STATUS;
	public String NAME;
	public String AGENT_VERSION;
	public String AGENT_BUILD;
	public String AGENT_IP;
	public Integer AGENT_HTTP_PORT;
	public Integer AGENT_HTTP_PORTS;
	public String AGENT_PROTOCOL;
	public Timestamp REGISTER_TIME;
	public Timestamp DISCOVERED_TIME;
	public Timestamp CHANGED_TIMESTAMP;
	public Timestamp UPDATE_TIMESTAMP;
	public Timestamp LAST_PROBE_TIME;
	
}
