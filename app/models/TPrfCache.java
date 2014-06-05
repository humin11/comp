/**
 * File Name：TPrfCache.java
 *
 * Version：
 * Date：2012-3-1
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TPrfCache
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-1 上午11:47:33
 * Last Modified By：tigaly
 * Last Modified：2012-3-1 上午11:47:33
 * Remarks：
 * @version 
 * 
 */

@Entity
@Table(name="T_PRF_CACHE",uniqueConstraints={@UniqueConstraint(columnNames={"TIME_ID","ELEMENT_ID"})})
public class TPrfCache extends PrfModel{
	public Long READ_NRM_IO;
	public Long WRITE_NRM_IO;
	public Long READ_SEQ_IO;
	public Long WRITE_SEQ_IO;
	public Long READ_NRM_HITS;
	public Long WRITE_NRM_HITS;
	public Long READ_SEQ_HITS;
	public Long WRITE_SEQ_HITS;
	
	public Long READ_KB;
	public Long WRITE_KB;
	public Long READ_TIME;
	public Long WRITE_TIME;
	
	public Long WRITE_PENDING;
	
	public Long CACHE_ALLOCATE;
	
	public Short M_CACHE_UTIL;
}
