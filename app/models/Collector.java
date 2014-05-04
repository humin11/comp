package models;

import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.ObjectId;
import play.db.ebean.Model;
import play.modules.mongodb.jackson.MongoDB;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Humin on 4/26/14.
 */
@Entity
@Table(name="t_config_collector")
public class Collector extends Model{

    @Id
    @ObjectId
    public String id;

    public String type;

    public String name;

    public String host;

    public String port;

    public String protocol;

    public String username;

    public String password;

    public String namespace;

    public static Model.Finder<Long,Collector> find = new Model.Finder<Long,Collector>(
            Long.class, Collector.class
    );
}
