package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_CHANGED_RECOVERY",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","CHANGED_TIME","CHANGED_LOG"})})
public class TChangedRecovery {
	@Id
	public String ID;
	public String ELEMENT_ID;
	public Timestamp CHANGED_TIME;
	public String CHANGED_LOG;
	
	public String PAIR_STATE;
	public String LINK_STATUS;
	public String MODE;
	
	public String SOURCE_LUN_STATE;
	public String TARGET_LUN_STATE;
	public String USER_ATTRIB1;
	public String USER_ATTRIB2;
	public String USER_ATTRIB3;
}
