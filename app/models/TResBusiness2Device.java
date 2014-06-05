package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_RES_BUSINESS2DEVICE")
public class TResBusiness2Device {
	  @Id
	  public Long ID;
	  public Long BUSINESS_ID;
	  public String DEVICE_ID;
	  public String SUB_DEVICE_ID;
	  public String DEVICE_TYPE;
}
