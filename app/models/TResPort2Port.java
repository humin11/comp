package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Humin on 4/28/14.
 */
@Entity
@Table(name="t_res_port2port")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResPort2Port extends Model {

    @Id
    public Long id;

    public TResPort source;

    public TResPort target;

}
