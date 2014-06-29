package discovery;

import utils.CIMCollector;
import utils.CIM_DataTypes;
import utils.CIM_Qualifiers;

import javax.cim.CIMObjectPath;
import javax.wbem.client.WBEMClient;

/**
 * Created by Humin on 5/1/14.
 */
public class CollectorImpl implements Collector {
    public CIM_DataTypes cim_DT = new CIM_DataTypes();
    public CIM_Qualifiers cim_Q = new CIM_Qualifiers();
    public WBEMClient cc;
    public CIMObjectPath instanceCOP;
    public CIMCollector collector;
    public String parentName;

    @Override
    public void init(WBEMClient client, CIMObjectPath path, CIMCollector coll, String pN) {

        this.cc = client;
        this.instanceCOP = path;
        this.collector = coll;
        this.parentName = pN;
    }

    @Override
    public void parser() {

    }
}
