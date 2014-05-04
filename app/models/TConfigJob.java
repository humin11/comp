package models;

import net.vz.mongodb.jackson.ObjectId;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Humin on 4/26/14.
 */
@Entity
@Table(name="t_config_job")
public class TConfigJob extends Model {

    @Id
    @ObjectId
    public String id;

    public String name;

    public Collector collector;

    public static Model.Finder<Long, TConfigJob> find = new Model.Finder<Long, TConfigJob>(
            Long.class, TConfigJob.class
    );

}
