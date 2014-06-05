package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TPrfSvcVdisk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PRF_SVC_VDISK")
public class TPrfSvcVdisk extends PrfModel{

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

	@Column(name = "READ_TIME", nullable = false)
	public Long READ_TIME;

	@Column(name = "WRITE_TIME", nullable = false)
	public Long WRITE_TIME;

	@Column(name = "PEAK_READ_TIME", nullable = false)
	public Integer PEAK_READ_TIME;

	@Column(name = "PEAK_WRITE_TIME", nullable = false)
	public Integer PEAK_WRITE_TIME;

	@Column(name = "GM_WRITES", nullable = false)
	public Long GM_WRITES;

	@Column(name = "GM_WRITE_OLAPS", nullable = false)
	public Long GM_WRITE_OLAPS;

	@Column(name = "GM_WRITE_TIME", nullable = false)
	public Long GM_WRITE_TIME;

	@Column(name = "READ_CACHE_KB", nullable = false)
	public Long READ_CACHE_KB;

	@Column(name = "WRITE_CACHE_KB", nullable = false)
	public Long WRITE_CACHE_KB;

	@Column(name = "READ_TRKS", nullable = false)
	public Long READ_TRKS;

	@Column(name = "WRITE_TRKS", nullable = false)
	public Long WRITE_TRKS;

	@Column(name = "WRITE_FAST_TRKS", nullable = false)
	public Long WRITE_FAST_TRKS;

	@Column(name = "WRITE_THRU_TRKS", nullable = false)
	public Long WRITE_THRU_TRKS;

	@Column(name = "WRITE_FLSH_TRKS", nullable = false)
	public Long WRITE_FLSH_TRKS;

	@Column(name = "WRITE_OFLW_TRKS", nullable = false)
	public Long WRITE_OFLW_TRKS;

	@Column(name = "PRESTAGE_TRKS", nullable = false)
	public Long PRESTAGE_TRKS;

	@Column(name = "DESTAGE_TRKS", nullable = false)
	public Long DESTAGE_TRKS;

	@Column(name = "READ_HIT_TRKS", nullable = false)
	public Long READ_HIT_TRKS;

	@Column(name = "PRESTAGE_HIT_TRKS", nullable = false)
	public Long PRESTAGE_HIT_TRKS;

	@Column(name = "DIRTY_WRITE_HIT_TRKS", nullable = false)
	public Long DIRTY_WRITE_HIT_TRKS;

	@Column(name = "HOST_TIME")
	public Long HOST_TIME;

	@Column(name = "M_NPRF_IO")
	public Long M_NPRF_IO;

	@Column(name = "M_NPRF_KB")
	public Long M_NPRF_KB;

	@Column(name = "M_VOL_UTIL")
	public Short M_VOL_UTIL;

	@Column(name = "DEV_ID", nullable = false)
	public Integer DEV_ID;

}