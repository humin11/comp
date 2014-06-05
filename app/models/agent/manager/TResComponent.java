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
@Table(name = "T_RES_COMPONENT", uniqueConstraints = { @UniqueConstraint(columnNames = { "TYPE", "NAME", "KEY_WORD"}) })
public class TResComponent {
	
	public TResComponent(){
		
	}

	@Id
	public String ID;

	/**
	 * Distribute Storage/Switch/Host/Database
	 */
	public String TYPE;
	
	public String NAME;

	public String STATUS;
	
	public String DESCRIPTION;
	
	public String KEY_WORD;

	@ManyToMany(mappedBy = "COMPONENTS", targetEntity = TResAgentSetting.class)
	public Set<TResAgentSetting> SETTINGS;
	
	@ManyToMany(targetEntity = TResProperty.class, fetch= FetchType.EAGER)
	@JoinTable(name = "T_RES_PROPERTY2COMPONENT", 
	        inverseJoinColumns=@JoinColumn(name="PROPERTIES_ID"))
	public Set<TResProperty> PROPERTIES;
	
	/**
	 * Create a new instance TResComponent.
	 *
	 * @param iD
	 * @param tYPE
	 * @param cOMPONENT_NAME
	 * @param sTATUS
	 */
	public TResComponent(String iD, String tYPE, String nAME,
			String sTATUS) {
		super();
		ID = iD;
		TYPE = tYPE;
		NAME = nAME;
		STATUS = sTATUS;
	}
	
}
