/**
 * File Name：TResTapeCartridge.java
 *
 * Version：
 * Date：2012-3-3
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import models.core.ResModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResTapeCartridge
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-3 下午11:52:51
 * Last Modified By：tigaly
 * Last Modified：2012-3-3 下午11:52:51
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_TAPE_CARTRIDGE",uniqueConstraints={@UniqueConstraint(columnNames={"NAME","SUBSYSTEM_ID"})})
public class TResTapeCartridge extends ResModel {
		public Long CAPACITY;
		public String MEDIA_TYPE;
		public Short CLEANER_MEDIA;
		public Short DUAL_SIDED;
		public String PHYSICAL_LABEL;
		public String MEDIA_DESCRIPTION;
}
