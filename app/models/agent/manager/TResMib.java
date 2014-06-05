package models.agent.manager;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_MIB", uniqueConstraints={@UniqueConstraint(columnNames={"OID"})})
public class TResMib {
	@Id
	public String ID;
	public String OID;
	public String NAME;
	public String MIB_NAME;
	public String MIB_VERSION;
	
	/**
	 * Belong to the type. Such as FabricRes, PortRes etc.
	 */
	public String MIB_TYPE;
	public String DESCRIPTION;
	public String ATTR1;
	public String ATTR2;
	public String ATTR3;
	public String ENUM;
	public String SCRIPT;
	@ManyToMany(mappedBy = "MIBS", targetEntity = TResProperty.class)
	public Set<TResProperty> PROPERTIES;
}
