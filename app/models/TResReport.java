package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="T_RES_REPORT",uniqueConstraints={@UniqueConstraint(columnNames={"REPORT_ID"})})
public class TResReport extends Model {
	public String REPORT_ID;
	public String REPORT_NAME;
	public String REPORT_DESCRIPTION;
	public String REPORT_CREATER;
	public Date CREATE_TIME;
	public String DEVICE_TYPE;

	public static Model.Finder<String, TResReport> find = new Model.Finder<String, TResReport>(
            String.class, TResReport.class
    );

    public static List<TResReport> findAll() { return find.all(); }
}
