package models;

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_RES_CPU_CORE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResCpuCore extends ResModel {
	public String CPU_ID;

}
