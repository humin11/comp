package models;

import javax.persistence.MappedSuperclass;

/**
 * Created by Humin on 5/3/14.
 */
@MappedSuperclass
public class ArrayPrfModel extends PrfModel {

    public Double total_io;
    public Double read_io;
    public Double write_io;
    public Double read_seq_io;
    public Double write_seq_io;
    public Double read_nrm_io;
    public Double write_nrm_io;

    public Double total_kb;
    public Double read_kb;
    public Double write_kb;
    public Double read_seq_kb;
    public Double write_seq_kb;
    public Double read_nrm_kb;
    public Double write_nrm_kb;

    public Double total_hits;
    public Double read_hits;
    public Double write_hits;
    public Double read_seq_hits;
    public Double write_seq_hits;
    public Double read_nrm_hits;
    public Double write_nrm_hits;

    public Double total_time;
    public Double read_time;
    public Double write_time;
    public Double read_seq_time;
    public Double write_seq_time;
    public Double read_nrm_time;
    public Double write_nrm_time;

}
