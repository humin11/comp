package models;

import net.vz.mongodb.jackson.ObjectId;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Humin on 4/26/14.
 */
@MappedSuperclass
public class AssetModel extends GenericModel {

    public String CAPTION;

    public String DEDICATED;

    public String DESCRIPTION;

    public String FIRMWARE_VERSION;

    public String OS_NAME;

    public String VERSION;

    public String CODE_LEVEL;

    public String MODEL;

    public String MANUFACTURE;

    public String VENDOR;

    public String ROLES;

    public String PRIMARY_OWNER_NAME;

    public String PRIMARY_OWNER_CONTACT;

    public String ACCESS_POINT;

    public String SERIAL_NUMBER;

    public String PART_NUMBER;

    public String IP_ADDRESS;

    public String UPTIME;

}
