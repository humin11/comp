package models;

import play.db.ebean.Model;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Humin on 5/3/14.
 */
@MappedSuperclass
public class PrfModel extends Model {

    public Long id;

    public String element_name;

    public String system_name;

    public int interval;

    public Date statistics_time;

}
