package discovery.array;

import discovery.Collector;
import discovery.CollectorImpl;
import utils.CIM_DataTypes;
import utils.CIM_Qualifiers;

import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.wbem.CloseableIterator;
import javax.wbem.WBEMException;
import javax.wbem.client.WBEMClient;

/**
 * Created by Humin on 5/1/14.
 */
public class StoragePool extends CollectorImpl {

    @Override
    public void parser(){

        try {
            CloseableIterator<CIMInstance> instances = this.cc.associatorInstances(this.instanceCOP,
                    "CIM_ComponentCS", "CIM_ComputerSystem",
                    "GroupComponent", "PartComponent", false, null);
            CIMInstance inst = null;
            while(instances.hasNext()){
                inst = instances.next();
                String name = this.cim_DT.getCIMInstancePropertyValueString(inst, "Name");
                String createClassName = this.cim_DT.getCIMInstancePropertyValueString(inst, "CreationClassName");
                if(!inst.getClassName().contains("StorageProcessorSystem")
                        && !inst.getClassName()
                        .contains("StorageLPARSystem")
                        && !inst.getClassName().contains("ONTAP_Node"))
                    continue;

            }
        } catch (WBEMException e) {

        }
    }

}
