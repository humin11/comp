package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_RES_APPLICATION", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResApplication extends Model{

    @GeneratedValue
    @Id
	public Long ID;
	public String NAME;
	public String DESCRIPTION;
    public String HOSTGROUP;
    public String SUBSYSTEM_ID;
    public Long CAPACITY;
    public Integer N_VOL;

    public static Model.Finder<String, TResApplication> find = new Model.Finder<String, TResApplication>(
            String.class, TResApplication.class
    );

    public static List<TResApplication> findAll() { return find.all(); }

    public static List<TResApplication> findBySubsystemId(String id) {
        return find.where().eq("SUBSYSTEM_ID",id).findList();
    }

    public static TResApplication findBySubsystemId(String id,String hostgroup) {
        List<TResApplication> apps = find.where().eq("SUBSYSTEM_ID",id).like("HOSTGROUP","%"+hostgroup+"%").findList();
        if(apps.isEmpty())
            return null;
        else
            return apps.get(0);
    }

}
