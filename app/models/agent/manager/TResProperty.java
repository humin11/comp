package models.agent.manager;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_PROPERTY", uniqueConstraints={@UniqueConstraint(columnNames={"NAME","PARSER_NAME"})})
public class TResProperty {
	@Id
	public String ID;
	public String NAME;
	public String PARSER_NAME;
	public String DESCRIPTION;
	public String ATTR1;
	public String ATTR2;
	public String ATTR3;
	public String SCRIPT;
	
	@ManyToMany(mappedBy = "PROPERTIES", targetEntity = TResComponent.class)
	public Set<TResComponent> COMPONENTS;
	
	@ManyToMany(targetEntity = TResMib.class, fetch= FetchType.EAGER)
	@JoinTable(name = "T_RES_PROPERTY2MIB", 
	        inverseJoinColumns=@JoinColumn(name="MIBS_ID"))
	public Set<TResMib> MIBS;
}
