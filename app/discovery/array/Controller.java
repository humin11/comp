package discovery.array;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import discovery.Collector;
import discovery.CollectorImpl;
import models.AssetModel;
import models.TResController;
import models.TResPort;
import play.Logger;
import play.libs.Json;
import utils.Constants;

import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.wbem.CloseableIterator;
import javax.wbem.WBEMException;
import javax.wbem.client.WBEMClient;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Humin on 5/1/14.
 */
public class Controller extends CollectorImpl {

    @Override
    public void parser() {


    }

}
