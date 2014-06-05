package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_RECOVERY",uniqueConstraints={@UniqueConstraint(columnNames={"GROUP_NAME","SOURCE_SYS_NAME","PAIR_NAME"})})
public class TResRecovery{
	@Id
	public String ID;
	public String PAIR_NAME;
	public String PAIR_STATE;
	public String LINK_STATUS;
	public String MODE;
	
	public String SOURCE_SYS_NAME;
	public String REMOTE_SYS_NAME;
	public String TRANSMIT_IDLE_TIME;
	public String GROUP_NAME;

	public String SOURCE_LUN_NAME;
	public String TARGET_LUN_NAME;
	public String SOURCE_LUN_STATE;
	public String TARGET_LUN_STATE;
	public String USER_ATTRIB1;
	public String USER_ATTRIB2;
	public String USER_ATTRIB3;
	public Timestamp UPDATE_TIME; 	
}
