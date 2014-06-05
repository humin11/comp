package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TPrfSvcComm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PRF_SVC_COMM")
public class TPrfSvcComm extends PrfModel {

	@Column(name = "SEND_IO", nullable = false)
	public Long SEND_IO;

	@Column(name = "RECV_IO", nullable = false)
	public Long RECV_IO;

	@Column(name = "SEND_KB", nullable = false)
	public Long SEND_KB;

	@Column(name = "RECV_KB", nullable = false)
	public Long RECV_KB;

	@Column(name = "SEND_E_TIME", nullable = false)
	public Long SEND_E_TIME;

	@Column(name = "RECV_E_TIME", nullable = false)
	public Long RECV_E_TIME;

	@Column(name = "SEND_Q_TIME", nullable = false)
	public Long SEND_Q_TIME;

	@Column(name = "RECV_Q_TIME", nullable = false)
	public Long RECV_Q_TIME;

	@Column(name = "DEV_ID", nullable = false)
	public Integer DEV_ID;

}