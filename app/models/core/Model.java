/**
 * File Name：Model.java
 *
 * Version：
 * Date：2012-3-11
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models.core;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：Model
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-11 下午2:41:35
 * Last Modified By：tigaly
 * Last Modified：2012-3-11 下午2:41:35
 * Remarks：
 * @version 
 * 
 */

@MappedSuperclass
public class Model {
	@Id
	public String ID;
	public String NAME;
}
