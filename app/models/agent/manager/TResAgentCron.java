package models.agent.manager;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_AGENT_CRON",uniqueConstraints={@UniqueConstraint(columnNames={"AGENT_ID","NAME"})})
public class TResAgentCron {

	@Id
	public String ID;
	
	public String AGENT_ID;
	/**
	 * configuration, performance, status, logging, alarm
	 */
	public String NAME;
	public String CRON_EXPRESSION;
}
