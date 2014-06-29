package discovery;

import models.AssetModel;
import utils.CIMCollector;

import javax.cim.CIMObjectPath;
import javax.wbem.client.WBEMClient;

/**
 * Created by Humin on 5/1/14.
 */
public interface Collector {

    public void init(WBEMClient client, CIMObjectPath path, CIMCollector coll, String parentName);

    public void parser();

}
