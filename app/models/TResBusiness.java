package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_RES_BUSINESS")
public class TResBusiness {
	@Id
	public Long ID;
	public String NAME;
	public String ADMINISTRATOR;
	public String DESCRIPTION;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
}
