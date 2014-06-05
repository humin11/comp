package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TPrfSvcSystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PRF_SVC_SYSTEM")
public class TPrfSvcSystem extends PrfModel {

	@Column(name = "NUM_PORTS", nullable = false)
	public Short NUM_PORTS;

	@Column(name = "NUM_MDISKS", nullable = false)
	public Short NUM_MDISKS;

	@Column(name = "NUM_VDISKS", nullable = false)
	public Short NUM_VDISKS;

	@Column(name = "CPU_UTIL_PERC", nullable = false)
	public Short M_UTIL_PERC;

	@Column(name = "COMM_LOCL_SEND_IO", nullable = false)
	public Long COMM_LOCL_SEND_IO;

	@Column(name = "COMM_LOCL_RECV_IO", nullable = false)
	public Long COMM_LOCL_RECV_IO;

	@Column(name = "COMM_LOCL_SEND_E_TIME", nullable = false)
	public Long COMM_LOCL_SEND_E_TIME;

	@Column(name = "COMM_LOCL_RECV_E_TIME", nullable = false)
	public Long COMM_LOCL_RECV_E_TIME;

	@Column(name = "COMM_LOCL_SEND_Q_TIME", nullable = false)
	public Long COMM_LOCL_SEND_Q_TIME;

	@Column(name = "COMM_LOCL_RECV_Q_TIME", nullable = false)
	public Long COMM_LOCL_RECV_Q_TIME;

	@Column(name = "COMM_RMOT_SEND_IO", nullable = false)
	public Long COMM_RMOT_SEND_IO;

	@Column(name = "COMM_RMOT_RECV_IO", nullable = false)
	public Long COMM_RMOT_RECV_IO;

	@Column(name = "COMM_RMOT_SEND_E_TIME", nullable = false)
	public Long COMM_RMOT_SEND_E_TIME;

	@Column(name = "COMM_RMOT_RECV_E_TIME", nullable = false)
	public Long COMM_RMOT_RECV_E_TIME;

	@Column(name = "COMM_RMOT_SEND_Q_TIME", nullable = false)
	public Long COMM_RMOT_SEND_Q_TIME;

	@Column(name = "COMM_RMOT_RECV_Q_TIME", nullable = false)
	public Long COMM_RMOT_RECV_Q_TIME;

	@Column(name = "PORT_HOST_SEND_IO", nullable = false)
	public Long PORT_HOST_SEND_IO;

	@Column(name = "PORT_HOST_RECV_IO", nullable = false)
	public Long PORT_HOST_RECV_IO;

	@Column(name = "PORT_HOST_SEND_KB", nullable = false)
	public Long PORT_HOST_SEND_KB;

	@Column(name = "PORT_HOST_RECV_KB", nullable = false)
	public Long PORT_HOST_RECV_KB;

	@Column(name = "PORT_DISK_SEND_IO", nullable = false)
	public Long PORT_DISK_SEND_IO;

	@Column(name = "PORT_DISK_RECV_IO", nullable = false)
	public Long PORT_DISK_RECV_IO;

	@Column(name = "PORT_DISK_SEND_KB", nullable = false)
	public Long PORT_DISK_SEND_KB;

	@Column(name = "PORT_DISK_RECV_KB", nullable = false)
	public Long PORT_DISK_RECV_KB;

	@Column(name = "PORT_LOCL_SEND_IO", nullable = false)
	public Long PORT_LOCL_SEND_IO;

	@Column(name = "PORT_LOCL_RECV_IO", nullable = false)
	public Long PORT_LOCL_RECV_IO;

	@Column(name = "PORT_LOCL_SEND_KB", nullable = false)
	public Long PORT_LOCL_SEND_KB;

	@Column(name = "PORT_LOCL_RECV_KB", nullable = false)
	public Long PORT_LOCL_RECV_KB;

	@Column(name = "PORT_RMOT_SEND_IO", nullable = false)
	public Long PORT_RMOT_SEND_IO;

	@Column(name = "PORT_RMOT_RECV_IO", nullable = false)
	public Long PORT_RMOT_RECV_IO;

	@Column(name = "PORT_RMOT_SEND_KB", nullable = false)
	public Long PORT_RMOT_SEND_KB;

	@Column(name = "PORT_RMOT_RECV_KB", nullable = false)
	public Long PORT_RMOT_RECV_KB;
	
	public Long FB_READ_IO;
	public Long FB_WRITE_IO;
	public Long FB_READ_KB;
	public Long FB_WRITE_KB;
	public Long FB_TOTAL_KB;
	public Long FB_TOTAL_IO;

	@Column(name = "MDSK_READ_IO", nullable = false)
	public Long BACK_READ_IO;

	@Column(name = "MDSK_WRITE_IO", nullable = false)
	public Long BACK_WRITE_IO;

	@Column(name = "MDSK_READ_KB", nullable = false)
	public Long BACK_READ_KB;

	@Column(name = "MDSK_WRITE_KB", nullable = false)
	public Long BACK_WRITE_KB;

	@Column(name = "MDSK_READ_E_TIME", nullable = false)
	public Long BACK_READ_TIME;

	@Column(name = "MDSK_WRITE_E_TIME", nullable = false)
	public Long BACK_WRITE_TIME;

	@Column(name = "MDSK_READ_Q_TIME", nullable = false)
	public Long BACK_READ_QUEUE_TIME;

	@Column(name = "MDSK_WRITE_Q_TIME", nullable = false)
	public Long BACK_WRITEQUEUE_TIME;

	@Column(name = "VDSK_READ_IO", nullable = false)
	public Long READ_IO;

	@Column(name = "VDSK_WRITE_IO", nullable = false)
	public Long WRITE_IO;
	
	public Long TOTAL_IO;

	@Column(name = "VDSK_READ_KB", nullable = false)
	public Long READ_KB;

	@Column(name = "VDSK_WRITE_KB", nullable = false)
	public Long WRITE_KB;
	
	public Long TOTAL_KB;

	@Column(name = "VDSK_READ_TIME", nullable = false)
	public Long READ_TIME;

	@Column(name = "VDSK_WRITE_TIME", nullable = false)
	public Long WRITE_TIME;

	@Column(name = "VDSK_PEAK_READ_TIME", nullable = false)
	public Integer PEAK_READ_TIME;

	@Column(name = "VDSK_PEAK_WRITE_TIME", nullable = false)
	public Integer PEAK_WRITE_TIME;

	@Column(name = "VDSK_GM_WRITES", nullable = false)
	public Long VDSK_GM_WRITES;

	@Column(name = "VDSK_GM_WRITE_OLAPS", nullable = false)
	public Long VDSK_GM_WRITE_OLAPS;

	@Column(name = "VDSK_GM_WRITE_TIME", nullable = false)
	public Long VDSK_GM_WRITE_TIME;

	@Column(name = "VDSK_READ_CACHE_KB", nullable = false)
	public Long READ_CACHE_KB;

	@Column(name = "VDSK_WRITE_CACHE_KB", nullable = false)
	public Long WRITE_CACHE_KB;

	@Column(name = "VDSK_READ_TRKS", nullable = false)
	public Long VDSK_READ_TRKS;

	@Column(name = "VDSK_WRITE_TRKS", nullable = false)
	public Long VDSK_WRITE_TRKS;

	@Column(name = "VDSK_WRITE_FAST_TRKS", nullable = false)
	public Long VDSK_WRITE_FAST_TRKS;

	@Column(name = "VDSK_WRITE_THRU_TRKS", nullable = false)
	public Long VDSK_WRITE_THRU_TRKS;

	@Column(name = "VDSK_WRITE_FLSH_TRKS", nullable = false)
	public Long VDSK_WRITE_FLSH_TRKS;

	@Column(name = "VDSK_WRITE_OFLW_TRKS", nullable = false)
	public Long VDSK_WRITE_OFLW_TRKS;

	@Column(name = "VDSK_PRESTAGE_TRKS", nullable = false)
	public Long VDSK_PRESTAGE_TRKS;

	@Column(name = "VDSK_DESTAGE_TRKS", nullable = false)
	public Long VDSK_DESTAGE_TRKS;

	@Column(name = "VDSK_READ_HIT_TRKS", nullable = false)
	public Long VDSK_READ_HIT_TRKS;

	@Column(name = "VDSK_PRESTAGE_HIT_TRKS", nullable = false)
	public Long VDSK_PRESTAGE_HIT_TRKS;

	@Column(name = "VDSK_DIRTY_WRITE_HIT_TRKS", nullable = false)
	public Long VDSK_DIRTY_WRITE_HIT_TRKS;

	@Column(name = "VDSK_HOST_TIME")
	public Long VDSK_HOST_TIME;

	@Column(name = "MDSK_PEAK_READ_E_TIME")
	public Long MDSK_PEAK_READ_E_TIME;

	@Column(name = "MDSK_PEAK_WRITE_E_TIME")
	public Long MDSK_PEAK_WRITE_E_TIME;

	@Column(name = "MDSK_PEAK_READ_Q_TIME")
	public Long MDSK_PEAK_READ_Q_TIME;

	@Column(name = "MDSK_PEAK_WRITE_Q_TIME")
	public Long MDSK_PEAK_WRITE_Q_TIME;

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

}