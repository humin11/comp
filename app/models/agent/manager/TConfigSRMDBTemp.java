package models.agent.manager;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_CONFIG_SRMDB_TEMP", uniqueConstraints={@UniqueConstraint(columnNames={"TABLE_NAME"})})
public class TConfigSRMDBTemp {
	@Id
	public String ID;
	public String TABLE_NAME;
	public Integer LAST_TIME;
	public Timestamp UPDATA_TIME;
}
