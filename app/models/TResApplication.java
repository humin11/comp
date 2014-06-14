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

    @Id
	public String ID;
	public String NAME;
	public String DESCRIPTION;
    public Long CAPACITY;
    public Integer N_VOL;

    public static Model.Finder<String, TResApplication> find = new Model.Finder<String, TResApplication>(
            String.class, TResApplication.class
    );

    public static List<TResApplication> findAll() { return find.all(); }

    public static TResApplication findByName(String name){
        List<TResApplication> list = find.where().eq("NAME",name).findList();
        if(list.isEmpty())
            return null;
        else
            return list.get(0);
    }
}
