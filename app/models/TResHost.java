package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Humin on 4/26/14.
 */
@Entity
@Table(name="t_res_host")
@Access(AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TResHost extends AssetModel {

    public static Finder<Long, TResHost> find = new Finder<Long, TResHost>(
            Long.class, TResHost.class
    );

    public static void create(JsonNode node){
        TResHost obj = find.where().eq("name",node.get("name").asText()).findUnique();
        if(obj==null){
            obj = new TResHost();
            obj = Json.fromJson(node, TResHost.class);
            obj.save();
        } else {
            Long id = obj.id;
            obj = Json.fromJson(node, TResHost.class);
            obj.update(id);
        }
    }

}
