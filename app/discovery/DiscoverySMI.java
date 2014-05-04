package discovery;

import discovery.array.Controller;
import models.*;
import play.Logger;
import play.libs.Json;
import utils.CIMCollector;
import utils.CIM_DataTypes;
import utils.CIM_Qualifiers;

import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.cim.UnsignedInteger16;
import javax.wbem.CloseableIterator;
import javax.wbem.WBEMException;
import javax.wbem.client.WBEMClient;

/**
 * Created by Humin on 4/26/14.
 */
public class DiscoverySMI {
    private WBEMClient client;
    private CIMCollector collector = null;
    private CIMObjectPath instanceCOP;
    private CIM_DataTypes cimDT = null;
    private CIM_Qualifiers cim_Q = new CIM_Qualifiers();

    public DiscoverySMI(String protocal, String host, String port, String namespace, String username, String password){

        collector = new CIMCollector();
        this.cimDT = new CIM_DataTypes();
        this.client = collector.connect(protocal,host,port,namespace, username, password);
        if(this.client!=null){
            this.parser(this.client, "CIM_ComputerSystem", namespace);
        }
    }

    private boolean parser(WBEMClient client, String cimClass, String namespace){

        CIMInstance instance = null;
        int count = 0;
        AssetModel cs = null;
        TResStorageSystem ss = null;
        TResSwitch sw = null;
        TResTape TResTape = null;
        TResHost TResHost = null;
        this.instanceCOP = new CIMObjectPath(null, null, null, namespace, cimClass, null);
        try {
            CloseableIterator<CIMInstance> cimInstances = client.enumerateInstances(this.instanceCOP, true, false, false, null);
            while(cimInstances.hasNext()){
                instance = cimInstances.next();
                cs = new AssetModel();
                UnsignedInteger16 dedicatedFill = new UnsignedInteger16("555");
                UnsignedInteger16[] dedicated = { dedicatedFill };
                int intDedicated1 = 0;
                int intDedicated2 = 0;
                int intDedicated3 = 0;
                int intDedicated4 = 0;
                int intDedicated5 = 0;
                int dedicatedSize = 0;
                String dedicates = "";
                try {
                    dedicated = this.cimDT.getUint16ArrayPropertyValue(instance, "Dedicated");
                    if(dedicated!=null){
                        dedicatedSize = dedicated.length;
                    }
                } catch (Exception e){
                    Logger.warn("Get dedicated failed. Messages:{}",e);
                }
                Logger.info("Dedicated size:"+dedicatedSize);
                if (dedicatedSize == 1) {
                    String dedicated1 = dedicated[0].toString();
                    intDedicated1 = Integer.parseInt(dedicated1);
                    dedicates = dedicated1;
                }
                else if (dedicatedSize == 2) {
                    String dedicated1 = dedicated[0].toString();
                    intDedicated1 = Integer.parseInt(dedicated1);
                    String dedicated2 = dedicated[1].toString();
                    intDedicated2 = Integer.parseInt(dedicated2);
                    dedicates = dedicated1 + "," + dedicated2;
                }
                else if (dedicatedSize == 3) {
                    String dedicated1 = dedicated[0].toString();
                    intDedicated1 = Integer.parseInt(dedicated1);
                    String dedicated2 = dedicated[1].toString();
                    intDedicated2 = Integer.parseInt(dedicated2);
                    String dedicated3 = dedicated[2].toString();
                    intDedicated3 = Integer.parseInt(dedicated3);
                    dedicates = dedicated1 + "," + dedicated2 + "," + dedicated3;
                }
                else if (dedicatedSize == 4) {
                    String dedicated1 = dedicated[0].toString();
                    intDedicated1 = Integer.parseInt(dedicated1);
                    String dedicated2 = dedicated[1].toString();
                    intDedicated2 = Integer.parseInt(dedicated2);
                    String dedicated3 = dedicated[2].toString();
                    intDedicated3 = Integer.parseInt(dedicated3);
                    String dedicated4 = dedicated[3].toString();
                    intDedicated4 = Integer.parseInt(dedicated4);
                    dedicates = dedicated1 + "," + dedicated2 + "," + dedicated3 + "," + dedicated4;
                }
                else if (dedicatedSize == 5) {
                    String dedicated1 = dedicated[0].toString();
                    intDedicated1 = Integer.parseInt(dedicated1);
                    String dedicated2 = dedicated[1].toString();
                    intDedicated2 = Integer.parseInt(dedicated2);
                    String dedicated3 = dedicated[2].toString();
                    intDedicated3 = Integer.parseInt(dedicated3);
                    String dedicated4 = dedicated[3].toString();
                    intDedicated4 = Integer.parseInt(dedicated4);
                    String dedicated5 = dedicated[4].toString();
                    intDedicated5 = Integer.parseInt(dedicated5);
                    dedicates = dedicated1 + "," + dedicated2 + "," + dedicated3 + "," + dedicated4 + "," + dedicated5;
                }

                Logger.debug("Dedicated={}", dedicates);
                String creationClassName = this.cimDT.getCIMInstancePropertyValueString(instance, "CreationClassName");
                collector.handleComputerSystem(client, instance.getObjectPath(), cs, instance, this.cim_Q, this.cimDT, dedicates, creationClassName);

                if ((!dedicates.equals("555")) &&
                        (!instance.getClassName().equals("LSISSI_StorageProcessorSystem")) &&
                        (!creationClassName.equals("HPEVA_StorageProcessorSystem")) &&
                        (!creationClassName.equals("HITACHI_StorageProcessorSystem")) &&
                        (!creationClassName.equals("Brocade_PhysicalComputerSystem")) &&
                        (!creationClassName.equals("CISCO_LogicalComputerSystem")) &&
                        (!creationClassName.equals("SunStorEdge_DSPStorageProcessorSystem")) &&
                        (!creationClassName.equals("OpenWBEM_UnitaryComputerSystem")) &&
                        (!creationClassName.equals("IBMTSSVC_IOGroup")) &&
                        (!creationClassName.equals("HPMSA_ArrayController")) && (
                        (dedicates.equals("3,15")) ||
                                (dedicates.equals("15,3")) ||
                                (dedicates.equals("3")) ||
                                (dedicates.equals("0")) ||
                                (dedicates.equals("3,15,16,25")) ||
                                (dedicates.equals("3,15,16,21,25")) ||
                                (dedicates.equals("3,15,25")) ||
                                (dedicates.equals("15")) ||
                                (dedicates.equals("5")) ||
                                (dedicates.equals("5,37")) ||
                                (dedicates.equals("3,22")) ||
                                (dedicates.equals("3,15,21")) ||
                                (dedicates.equals("15,21"))))
                {
                    if (((intDedicated1 == 3) && (intDedicated2 == 15) && (intDedicated3 != 25)) || ((intDedicated1 == 15) && (intDedicated2 == 3) && (intDedicated3 != 25))) {
                        Logger.debug("ComputerSystem for Block");
                        ss = Json.fromJson(Json.toJson(cs), TResStorageSystem.class);
                        try {
                            String nvsramVersion = this.cimDT.getCIMInstancePropertyValueString(instance, "NVSRAMVersion");
                            ss.nvsram_version = nvsramVersion;
                        } catch (Exception e){

                        }

                        try {
                            int cacheBlockSize = this.cimDT.getCIMInstancePropertyUnsignedInt64Value(instance, "CacheBlockSize").intValue();
                            ss.cache_block = cacheBlockSize;
                        } catch (Exception e){

                        }

                        //Send Storage System
                        TResStorageSystem.create(Json.toJson(ss));

                        //For Get Controllers and Ports
                        collector.getController(instance.getObjectPath(), this.cimDT, this.cim_Q, ss.name, creationClassName);
                        collector.getStoragePool(instance.getObjectPath(), this.cimDT, this.cim_Q, ss.name, creationClassName);
                        collector.getDiskDrive(instance.getObjectPath(), this.cimDT, this.cim_Q, ss.name, creationClassName);
                        count++;

                    } else if ((intDedicated1 == 15) && (intDedicated2 == 21)){
                        Logger.debug("ComputerSystem for OS");
                        count ++;

                    } else if ((intDedicated1 == 3) && (intDedicated2 == 15) && (intDedicated3 == 25)) {
                        Logger.debug("ComputerSystem for NAS");
                        ss = Json.fromJson(Json.toJson(cs), TResStorageSystem.class);
                        TResStorageSystem.create(Json.toJson(ss));
                        count ++;

                    } else if (intDedicated1 == 0) {
                        Logger.debug("ComputerSystem for OS");
                        TResHost = Json.fromJson(Json.toJson(cs), TResHost.class);
                        TResHost.create(Json.toJson(TResHost));
                        count ++;

                    } else if ((intDedicated1 == 5) && (!creationClassName.equals("CISCO_LogicalComputerSystem"))){
                        Logger.debug("ComputerSystem for Switch");
                        sw = Json.fromJson(Json.toJson(cs), TResSwitch.class);
                        TResSwitch.create(Json.toJson(sw));
                        count ++;

                    } else if ((intDedicated1 == 3) && (intDedicated2 == 22)) {
                        Logger.debug("ComputerSystem for Tape");
                        TResTape = Json.fromJson(Json.toJson(cs), TResTape.class);
                        TResTape.create(Json.toJson(TResTape));
                        count ++;

                    } else if ((intDedicated1 == 15) && (creationClassName.equals("VMWARE_ESXComputerSystem"))) {
                        Logger.debug("ComputerSystem for VMWare ESX");
                        TResHost = Json.fromJson(Json.toJson(cs), TResHost.class);
                        TResHost.create(Json.toJson(TResHost));
                        count ++;

                    } else {
                        Logger.debug("ComputerSystem for Nothing");
//                        count ++;

                    }
                }
            }
        } catch (WBEMException e) {
            e.printStackTrace();
        }

        Logger.info("Discovery "+count+" Devices.");
        return true;
    }


}
