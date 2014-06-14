package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_RES_APPLICATION2LUN", uniqueConstraints = {@UniqueConstraint(columnNames = {"APPLICATION_ID","HOSTGROUP"})})
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResApplication2Lun extends Model{

    @GeneratedValue
    @Id
	public Long ID;
	public String APPLICATION_ID;
	public String APPLICATION_NAME;
    public String HOSTGROUP;
    public String SUBSYSTEM_ID;
    public String VOLUME_ID;

    public static Model.Finder<String, TResApplication2Lun> find = new Model.Finder<String, TResApplication2Lun>(
            String.class, TResApplication2Lun.class
    );

    public static List<TResApplication2Lun> findAll() { return find.all(); }

    public static List<TResApplication2Lun> findByApplicationId(String id) {
        return find.where().eq("APPLICATION_ID",id).findList();
    }

    public static List<TResApplication2Lun> findBySubsystemId(String id,String hostgroup) {
        return find.where().eq("SUBSYSTEM_ID",id).eq("HOSTGROUP",hostgroup).findList();
    }

}
