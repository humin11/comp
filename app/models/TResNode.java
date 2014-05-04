package models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Humin on 4/28/14.
 */
@Entity
@Table(name="t_res_node")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResNode extends GenericModel {

    public TResHost TResHost;

    public String permanent_address;

}
