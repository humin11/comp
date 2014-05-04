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

    public String caption;

    public String dedicated;

    public String description;

    public String firmware_version;

    public String os_name;

    public String version;

    public String code_level;

    public String model;

    public String manufacture;

    public String vendor;

    public String roles;

    public String primary_owner_name;

    public String primary_owner_contact;

    public String access_point;

    public String serial_number;

    public String part_number;

    public String ip_address;

    public String uptime;

}
