package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TPrfSvcPort entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PRF_SVC_PORT")
public class TPrfSvcPort extends PrfModel {

	@Column(name = "HOST_SEND_IO", nullable = false)
	public Long HOST_SEND_IO;

	@Column(name = "HOST_RECV_IO", nullable = false)
	public Long HOST_RECV_IO;

	@Column(name = "HOST_SEND_KB", nullable = false)
	public Long HOST_SEND_KB;

	@Column(name = "HOST_RECV_KB", nullable = false)
	public Long HOST_RECV_KB;

	@Column(name = "DISK_SEND_IO", nullable = false)
	public Long DISK_SEND_IO;

	@Column(name = "DISK_RECV_IO", nullable = false)
	public Long DISK_RECV_IO;

	@Column(name = "DISK_SEND_KB", nullable = false)
	public Long DISK_SEND_KB;

	@Column(name = "DISK_RECV_KB", nullable = false)
	public Long DISK_RECV_KB;

	@Column(name = "LOCL_SEND_IO", nullable = false)
	public Long LOCL_SEND_IO;

	@Column(name = "LOCL_RECV_IO", nullable = false)
	public Long LOCL_RECV_IO;

	@Column(name = "LOCL_SEND_KB", nullable = false)
	public Long LOCL_SEND_KB;

	@Column(name = "LOCL_RECV_KB", nullable = false)
	public Long LOCL_RECV_KB;

	@Column(name = "RMOT_SEND_IO", nullable = false)
	public Long RMOT_SEND_IO;

	@Column(name = "RMOT_RECV_IO", nullable = false)
	public Long RMOT_RECV_IO;

	@Column(name = "RMOT_SEND_KB", nullable = false)
	public Long RMOT_SEND_KB;

	@Column(name = "RMOT_RECV_KB", nullable = false)
	public Long RMOT_RECV_KB;

	@Column(name = "M_SEND_BNDW_PERC")
	public Short M_SEND_BNDW_PERC;

	@Column(name = "M_RECV_BNDW_PERC")
	public Short M_RECV_BNDW_PERC;

	@Column(name = "LINK_FAILURES")
	public Long LINK_FAILURES;

	@Column(name = "LOSS_OF_SYNC_COUNT")
	public Long LOSS_OF_SYNC_COUNT;

	@Column(name = "LOSS_OF_SIGNAL_COUNT")
	public Long LOSS_OF_SIGNAL_COUNT;

	@Column(name = "CRC_ERRORS")
	public Long CRC_ERRORS;

	@Column(name = "PRIMITIVE_SEQ_PRT_ERR_CNT")
	public Long PRIMITIVE_SEQ_PRT_ERR_CNT;

	@Column(name = "INVALID_TRANSMISSION_WORDS")
	public Long INVALID_TRANSMISSION_WORDS;

	@Column(name = "ZERO_BB_CREDIT_TIME")
	public Long ZERO_BB_CREDIT_TIME;

	@Column(name = "DEV_ID", nullable = false)
	public Integer DEV_ID;

}