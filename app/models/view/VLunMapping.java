/**
 * File Name：VLunMapping.java 
 * 
 * Version： 
 * Date：2012-3-29 
 * Copyright CloudWei Dev Team 2012  
 * All Rights Reserved. 
 *
 */
package models.view;


import models.core.ResModel;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 
 * Project Name：istorm_v2.0 
 * Class Name：VLunMapping 
 * Class Desc： 
 * Author：Fred 
 * Create Date：2012-3-29 上午9:36:36 
 * Last Modified By：Fred 
 * Last Modified：2012-3-29 上午9:36:36 
 * Remarks： 
 * @version  
 * 
 */
@Entity
@Table(name="V_LUN_MAPPING")
public class VLunMapping {
	
	public String HBA_PORT_WWN;
	public String FA_PORT_NAME;
	public String VOLUME_ID;
	public String VOLUME_NAME;
	public Long VOLUME_CAP;
	public String HOST_NAME;
	public String STATUS;
	public String SUBSYSTEM_ID;

    public static Model.Finder<String, VLunMapping> find = new Model.Finder<String, VLunMapping>(
            String.class, VLunMapping.class
    );

    public static List<VLunMapping> findBySubsystemId(String id) {
        return find.where().eq("SUBSYSTEM_ID",id).findList();
    }

}
