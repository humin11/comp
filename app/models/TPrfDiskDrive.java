package models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Humin on 5/3/14.
 */
@Entity
@Table(name="t_prf_disk_drive")
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TPrfDiskDrive extends ArrayPrfModel {

}
