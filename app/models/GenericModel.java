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
    public Long ID;

    @Constraints.Required
    public String NAME;

    @Constraints.Required
    public String ELEMENT_NAME;

    @Constraints.Required
    public String DISPLAY_NAME;

    public String CREATION_CLASS_NAME;

    public String NAME_FORMAT;

    public int REQUESTED_STATE;

    public int HEALTH_STATE;

    public int ENABLED_STATE;

    public int ENABLED_DEFAULT;

    public String OPERATIONAL_STATUS;

    public String STATUS;

    public String STATUS_DESCRIPTION;

    public String SUBSYSTEM_NAME;

    public String SUBSYSTEM_ID;

    public Date DISCOVERY_AT = new Date();

    public Date UPDATE_AT = new Date();
}
