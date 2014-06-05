package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TPrfSvcMdisk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PRF_SVC_MDISK")
public class TPrfSvcMdisk extends PrfModel {

	@Column(name = "NUM_NODES", nullable = false)
	public Short NUM_NODES;

	@Column(name = "READ_IO", nullable = false)
	public Long READ_IO;

	@Column(name = "WRITE_IO", nullable = false)
	public Long WRITE_IO;

	@Column(name = "READ_KB", nullable = false)
	public Long READ_KB;

	@Column(name = "WRITE_KB", nullable = false)
	public Long WRITE_KB;

	@Column(name = "READ_E_TIME", nullable = false)
	public Long READ_E_TIME;

	@Column(name = "WRITE_E_TIME", nullable = false)
	public Long WRITE_E_TIME;

	@Column(name = "READ_Q_TIME", nullable = false)
	public Long READ_Q_TIME;

	@Column(name = "WRITE_Q_TIME", nullable = false)
	public Long WRITE_Q_TIME;

	@Column(name = "PEAK_READ_E_TIME")
	public Long PEAK_READ_E_TIME;

	@Column(name = "PEAK_WRITE_E_TIME")
	public Long PEAK_WRITE_E_TIME;

	@Column(name = "PEAK_READ_Q_TIME")
	public Long PEAK_READ_Q_TIME;

	@Column(name = "PEAK_WRITE_Q_TIME")
	public Long PEAK_WRITE_Q_TIME;

	@Column(name = "DEV_ID", nullable = false)
	public Integer DEV_ID;

}