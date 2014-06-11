/**
 * File Name：TResRaidGroup2Vol.java
 *
 * Version：
 * Date：2012-2-29
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import play.db.ebean.Model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResRaidGroup2Vol
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-2-29 下午4:28:14
 * Last Modified By：tigaly
 * Last Modified：2012-2-29 下午4:28:14
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_RES_RAIDGROUP2VOL",uniqueConstraints={@UniqueConstraint(columnNames={"RAIDGROUP_ID","VOLUME_ID"})})
public class TResRaidGroup2Vol {
	@Id
	public String ID;
	public String RAIDGROUP_ID;
	public String VOLUME_ID;
	public Timestamp CREATE_TIME;
	public Timestamp UPDATE_TIME;
	public Short OPERATIONAL_STATUS;

    public static Model.Finder<String, TResRaidGroup2Vol> find = new Model.Finder<String, TResRaidGroup2Vol>(
            String.class, TResRaidGroup2Vol.class
    );

    public static int countByRaidGroupId(String raidgroupId){
        return find.where().eq("RAIDGROUP_ID",raidgroupId).findRowCount();
    }

    public static TResRaidGroup2Vol findByVolumeId(String volumeId){
        List<TResRaidGroup2Vol> list = find.where().eq("VOLUME_ID",volumeId).findList();
        if(list.isEmpty())
            return null;
        else
            return list.get(0);
    }
}
