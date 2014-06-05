package models.agent.manager;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "T_RES_AGENT_SETTING", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"AGENT_ID", "IP_ADDRESS", "PORT" }) })
public class TResAgentSetting{
	
	public TResAgentSetting(){
		
	}
	
	@Id
	public String ID;

	public String AGENT_ID;
	/**
	 * Display Name
	 */
	public String NAME;
	/**
	 * SMI-S/SNMP/CLI/SRMDB
	 */
	public String AGENT_TYPE;
	/**
	 * Distribute HTTP/HTTPS
	 */
	public String PROTOCOL;
	/**
	 * IP Address
	 */
	public String IP_ADDRESS;
	/**
	 * PORT
	 */
	public Integer PORT;
	/**
	 * NAMESPACE for SMI-S
	 */
	public String NAMESPACE;
	/**
	 * 1 - SNMP v1 <br />
	 * 2 - SNMP v2c <br />
	 * 3 - SNMP v3
	 */
	public String SNMP_VERSION;
	/**
	 * public / private
	 */
	public String SNMP_COMMUNITY;
	/**
	 * 
	 */
	@Column(length=2000)
	public String SNMP_ENTERPRICE_OID;
	/**
	 * Distribute SSH/Telnet
	 */
	public String LOGIN_PROTOCOL;
	public String USERNAME;
	public String PASSWORD;
	/**
	 * 0 - Okay <br />
	 * 1 - Pause
	 */
	public String STATUS;

//	@ElementCollection(targetClass=TResComponent.class)
	@ManyToMany(targetEntity = TResComponent.class, fetch= FetchType.EAGER)
	@JoinTable(name = "T_RES_SCAN2COMPONENT", 
	        inverseJoinColumns=@JoinColumn(name="component_id"))
	public Set<TResComponent> COMPONENTS;
	
//	@OneToMany(mappedBy="SETTING")
//	public List<TResProtocolPropertyValue> PROPERTIES;

	
	/**
	 * Create a new instance TResAgentSetting.
	 *
	 * @param iD
	 * @param aGENT_ID
	 * @param nAME
	 * @param aGENT_TYPE
	 * @param pROTOCOL
	 * @param iP_ADDRESS
	 * @param pORT
	 * @param nAMESPACE
	 * @param sNMP_VERSION
	 * @param sNMP_COMMUNITY
	 * @param sNMP_ENTERPRICE_OID
	 * @param lOGIN_PROTOCOL
	 * @param uSERNAME
	 * @param pASSWORD
	 * @param sTATUS
	 */
	public TResAgentSetting(String iD, String aGENT_ID, String nAME,
			String aGENT_TYPE, String pROTOCOL, String iP_ADDRESS,
			Integer pORT, String nAMESPACE, String sNMP_VERSION,
			String sNMP_COMMUNITY, String sNMP_ENTERPRICE_OID,
			String lOGIN_PROTOCOL, String uSERNAME, String pASSWORD,
			String sTATUS) {
		super();
		ID = iD;
		AGENT_ID = aGENT_ID;
		NAME = nAME;
		AGENT_TYPE = aGENT_TYPE;
		PROTOCOL = pROTOCOL;
		IP_ADDRESS = iP_ADDRESS;
		PORT = pORT;
		NAMESPACE = nAMESPACE;
		SNMP_VERSION = sNMP_VERSION;
		SNMP_COMMUNITY = sNMP_COMMUNITY;
		SNMP_ENTERPRICE_OID = sNMP_ENTERPRICE_OID;
		LOGIN_PROTOCOL = lOGIN_PROTOCOL;
		USERNAME = uSERNAME;
		PASSWORD = pASSWORD;
		STATUS = sTATUS;
	}

}
