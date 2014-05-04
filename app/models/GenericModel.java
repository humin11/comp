package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Humin on 4/28/14.
 */
@MappedSuperclass
public class GenericModel extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String element_name;

    @Constraints.Required
    public String display_name;

    public String creation_class_name;

    public String name_format;

    public int requested_state;

    public int health_state;

    public int enabled_state;

    public int enabled_default;

    public String operational_status;

    public String status;

    public String status_description;

    public String system_name;

    public Date discovery_at = new Date();

    public Date update_at = new Date();
}
