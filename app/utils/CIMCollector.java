package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.*;
import play.Logger;
import play.libs.Json;

import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.cim.UnsignedInteger16;
import javax.security.auth.Subject;
import javax.wbem.CloseableIterator;
import javax.wbem.WBEMException;
import javax.wbem.client.PasswordCredential;
import javax.wbem.client.UserPrincipal;
import javax.wbem.client.WBEMClient;
import javax.wbem.client.WBEMClientFactory;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Humin on 4/26/14.
 */
public class CIMCollector {
    private WBEMClient client = null;
    private String nameSpace = "";
    private CIMObjectPath path = null;
    private String CN = "CIMCollector";
    private CloseableIterator<?> instanceEnum = null;


    public WBEMClient connect(String protocol, String host, String port,
                           String namespace, String user, String password) {
        this.nameSpace = namespace;
        if(!this.nameSpace.startsWith("/")){
            this.nameSpace = "/" + this.nameSpace;
        }

        String unsecureClientNameSpace = protocol + "://" + host + ":" + port + "/" + namespace;
        Logger.info(this.CN + " Client Host = " + host);
        Logger.info(this.CN + " Client NameSpace = " + namespace);
        Logger.info(this.CN + " Client Port = " + port);
        Logger.info(this.CN + " Client URI String = " + unsecureClientNameSpace);

        try {
            client = WBEMClientFactory.getClient("CIM-XML");

            path = new CIMObjectPath(unsecureClientNameSpace);
            Subject subject = new Subject();
            subject.getPrincipals().add(new UserPrincipal(user));
            subject.getPrivateCredentials().add(
                    new PasswordCredential(password));
            Locale[] l = { Locale.ENGLISH };
            client.initialize(path, subject, l);
            CIMInstance next = null;


            try {
                this.instanceEnum = client.enumerateInstances(new CIMObjectPath(null, null, null, this.nameSpace, "CIM_ComputerSystem", null), true, false, true, null);
            }catch (WBEMException ce){
                Logger.error("Error:{}",ce);
                Logger.error("Enum failed, cannot emum anything.");
                this.instanceEnum = null;
            }

        } catch (WBEMException e) {
            Logger.error(this.CN + " Issue w/ provider");
            Logger.error("Error:{}", e);
            this.client = null;
            //Send Error Message to Job Manager
        }
        return this.client;
    }

    public Object compare(Object obj, JsonNode node){
        Iterator<String> it = node.fieldNames();
        String key="";
        Field field = null;
        while(it.hasNext()) {
            key = it.next();
            if(node.has(key) && !key.equalsIgnoreCase("id") && node.hasNonNull(key)) {

                try {
                    field = obj.getClass().getField(key);
                    field.setAccessible(true);

                    if (field.getType() == String.class) {
                        Logger.info(key+"Fuck you!!!!" + node.get(key).asText());
                        field.set(obj, node.get(key).asText());
                    } else if (field.getType().getCanonicalName().contains("Boolean")) {
                        if (field.getBoolean(key) == node.get(key).asBoolean())
                            field.set(obj, node.get(key).asBoolean());
                    } else if (field.getType().getCanonicalName().contains("Double")) {
                        if (field.getDouble(key) == node.get(key).asDouble())
                            field.set(obj, node.get(key).asDouble());
                    } else if (field.getType().getCanonicalName().contains("Int")) {
                        if (field.getInt(key) == node.get(key).asInt())
                            field.set(obj, node.get(key));
                    } else if (field.getType().getCanonicalName().contains("Long")) {
                        if (field.getLong(key) == node.get(key).asLong())
                            field.set(obj, node.get(key));
                    }

                } catch (NoSuchFieldException e) {

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    public void handleComputerSystem(WBEMClient client, CIMObjectPath op, AssetModel cs, CIMInstance instance, CIM_Qualifiers cim_Q, CIM_DataTypes cimDT, String dedicates, String creationClassName){

        cs.creation_class_name = creationClassName;
        cs.dedicated = dedicates;

        try {
            String elementName = cimDT.getCIMInstancePropertyValueString(instance, "ElementName");
            cs.element_name = elementName;
            Logger.debug("Discovery Element:{}", elementName);
        } catch (Exception e){

        }

        try {
            String name = cimDT.getCIMInstancePropertyValueString(instance, "Name");
            String nameFormat = cimDT.getCIMInstancePropertyValueString(instance, "NameFormat");
            cs.name = name;
            cs.name_format = nameFormat;
        }catch (Exception e){

        }

        if(cs.element_name!=null && !"".equals(cs.element_name)){
            cs.display_name = cs.element_name;
        } else {
            cs.display_name = cs.name;
        }

        try {
            String description = cimDT.getCIMInstancePropertyValueString(instance, "Description");
            cs.description = description;
        } catch (Exception e){

        }

        try {
            String primaryOwnerContact = cimDT.getCIMInstancePropertyValueString(instance, "PrimaryOwnerContact");
            cs.primary_owner_contact = primaryOwnerContact;
        } catch (Exception e){

        }

        try {
            String primaryOwnerName = cimDT.getCIMInstancePropertyValueString(instance, "PrimaryOwnerName");
            cs.primary_owner_name = primaryOwnerName;
        } catch (Exception e){

        }

        try {
            String caption = cimDT.getCIMInstancePropertyValueString(instance, "Caption");
            cs.caption = caption;
        } catch (Exception e){

        }

        try {
            String status = cimDT.getCIMInstancePropertyValueString(instance, "Status");
            cs.status = status;
        } catch (Exception e){

        }

        try {
            String firmwareVersion = cimDT.getCIMInstancePropertyValueString(instance, "FirmwareVersion");
            cs.firmware_version = firmwareVersion;
        } catch (Exception e){

        }

        try {
            String codeLevel = cimDT.getCIMInstancePropertyValueString(instance, "CodeLevel");
            if(cs.firmware_version==null || "".equalsIgnoreCase(cs.firmware_version))
                cs.firmware_version = codeLevel;
            cs.code_level = codeLevel;
        } catch (Exception e){

        }

        try {
            int requestedState = cimDT.getCIMInstancePropertyUnsignedInt16Value(instance, "RequestedState").intValue();
            cs.requested_state = requestedState;
        } catch (Exception e){

        }

        try {
            int enabledDefault = cimDT.getCIMInstancePropertyUnsignedInt16Value(instance, "EnabledDefault").intValue();
            cs.enabled_default = enabledDefault;
        } catch (Exception e){

        }

        try {
            int enabledDefault = cimDT.getCIMInstancePropertyUnsignedInt16Value(instance, "EnabledDefault").intValue();
            cs.enabled_default = enabledDefault;
        } catch (Exception e){

        }

        try {
            int enabledDefault = cimDT.getCIMInstancePropertyUnsignedInt16Value(instance, "EnabledDefault").intValue();
            cs.enabled_default = enabledDefault;
        } catch (Exception e){

        }

        try
        {
            UnsignedInteger16[] operationalStatusArray = cimDT.getUint16ArrayPropertyValue(instance, "OperationalStatus");
            int operationalStatusSize = 0;
            if (operationalStatusArray != null) {
                operationalStatusSize = operationalStatusArray.length;
            }
            Logger.debug("operationalStatusSize = " + operationalStatusSize);
            Vector operationalStatusString = new Vector();
            for (int x = 0; x < operationalStatusSize; x++) {
                UnsignedInteger16 opstsint = operationalStatusArray[x];

                int operationalStatusInt = Integer.parseInt(opstsint.toString());

                String operationalStatusValue = cim_Q.operationalStatus(operationalStatusInt);

                operationalStatusString.add(operationalStatusValue);
            }
            String operationalStatusFinal = cim_Q.buildStringFromVector(operationalStatusString, ",");
            cs.operational_status =operationalStatusFinal;
            if(cs.status==null || cs.status.equalsIgnoreCase("none")){
                cs.status = cs.operational_status;
            }
        } catch (Exception e) {
            Logger.error("OperationalStatus:{}", e);
            cs.operational_status = "Unknown";
        }

        String statusDescriptionsFinal = null;
        try
        {
            String[] statusDescriptionsArray = cimDT.getStringArrayPropertyValue(instance, "StatusDescriptions");
            int statusDescriptionsSize = 0;
            if (statusDescriptionsArray != null) {
                statusDescriptionsSize = statusDescriptionsArray.length;
            }
            Logger.debug("statusDescriptionsSize = " + statusDescriptionsSize);
            Vector statusDescriptionsString = new Vector();
            for (int y = 0; y < statusDescriptionsSize; y++)
            {
                String statusDescriptionsValue = statusDescriptionsArray[y].toString();

                statusDescriptionsString.add(statusDescriptionsValue);
            }
            statusDescriptionsFinal = cim_Q.buildStringFromVector(statusDescriptionsString, ",");
        } catch (Exception e) {
            Logger.error("StatusDescriptions:{}", e);
        }

        String identifyingDescriptionsFinal = null;
        String[] identifyingDescriptionsArray = null;
        try
        {
            identifyingDescriptionsArray = cimDT.getStringArrayPropertyValue(instance, "IdentifyingDescriptions");
            int identifyingDescriptionsSize = 0;
            if (identifyingDescriptionsArray != null) {
                identifyingDescriptionsSize = identifyingDescriptionsArray.length;
            }
            Logger.debug("identifyingDescriptinsSize = " + identifyingDescriptionsSize);
            Vector identifyingDescriptionsString = new Vector();

            for (int y = 0; y < identifyingDescriptionsSize; y++)
            {
                String identfyingDescriptionsValue = identifyingDescriptionsArray[y].toString();

                identifyingDescriptionsString.add(identfyingDescriptionsValue);


            }
            identifyingDescriptionsFinal = cim_Q.buildStringFromVector(identifyingDescriptionsString, ",");


            Logger.info("IdentifyingDescriptions = " + identifyingDescriptionsFinal);
        } catch (Exception e) {
            Logger.error("IdentifyingDescriptions", e);
        }

        String otherIdentifyingInfoFinal = null;
        String[] otherIdentifyingInfoArray = null;
        try
        {
            otherIdentifyingInfoArray = cimDT.getStringArrayPropertyValue(instance, "OtherIdentifyingInfo");
            int otherIdentifyingInfoSize = 0;
            if (otherIdentifyingInfoArray != null) {
                otherIdentifyingInfoSize = otherIdentifyingInfoArray.length;
            }
            Logger.debug("otherIdentifyingInfoSize = " + otherIdentifyingInfoSize);
            Vector otherIdentifyingInfoString = new Vector();
            Vector<String> _v_ip = new Vector<String>();
            for (int y = 0; y < otherIdentifyingInfoSize; y++)
            {
                String otherIdentifyingInfoValue = otherIdentifyingInfoArray[y].toString();

                otherIdentifyingInfoString.add(otherIdentifyingInfoValue);
                if(identifyingDescriptionsArray!=null && identifyingDescriptionsArray.length>y && identifyingDescriptionsArray[y]!=null && identifyingDescriptionsArray[y].toLowerCase().contains("ipv4")){
                    _v_ip.add(otherIdentifyingInfoValue);
                }
            }
            otherIdentifyingInfoFinal = cim_Q.buildStringFromVector(otherIdentifyingInfoString, ",");
            cs.ip_address = cim_Q.buildStringFromVector(_v_ip,",");
            Logger.info("OtherIdentifyingInfo = " + otherIdentifyingInfoFinal);
        } catch (Exception e) {
            Logger.error("OtherIdentifyingInfo", e);
        }



        this.getPhysicalPackage(cs, op, cimDT, cim_Q, creationClassName);

        this.getSoftwareInstation(cs, op, cimDT, cim_Q, creationClassName);
    }

    private void getPhysicalPackage(AssetModel cs, CIMObjectPath op, CIM_DataTypes cimDT, CIM_Qualifiers cim_Q, String creationClassName){
        try
        {
            CIMInstance cim_PhysicalPackageCI = null;
            Vector<String> models = new Vector<String>();
            Vector<String> manufactures = new Vector<String>();
            Vector<String> serial_numbers = new Vector<String>();
            Vector<String> part_numbers = new Vector<String>();
            Vector<String> versions = new Vector<String>();
            String model = null;
            String manufacture = null;
            String serial_number= null;
            String part_number= null;
            String version = null;
            CloseableIterator cim_PhysicalPackageEnum = client.associatorInstances(op,
                    "CIM_SystemPackaging", "CIM_PhysicalPackage",
                    "Dependent", "Antecedent", false,
                    null);
            if(cim_PhysicalPackageEnum==null || !cim_PhysicalPackageEnum.hasNext()){
                cim_PhysicalPackageEnum = client.associatorInstances(op,
                        "CIM_Realizes", "CIM_PhysicalPackage",
                        "Dependent", "Antecedent", false,
                        null);
            }
            while (cim_PhysicalPackageEnum.hasNext()) {
                Logger.debug(" Enumerated PhysicalPackage and has more elements");
                cim_PhysicalPackageCI = (CIMInstance)cim_PhysicalPackageEnum.next();

                if (cim_PhysicalPackageEnum != null) {
                    try {
                        model = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "Model");
                        if(model!=null && !models.contains(model)){
                            models.add(model);
                        }
                    } catch (NullPointerException npe) {
                    }
                    try {
                        manufacture = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "Manufacturer");
                        if(manufacture!=null && !manufactures.contains(manufacture)){
                            manufactures.add(manufacture);
                        }
                    } catch (NullPointerException npe) {
                    }

                    try {
                        serial_number = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "SerialNumber");
                        if(serial_number!=null && !serial_numbers.contains(serial_number)){
                            serial_numbers.add(serial_number);
                        }
                    } catch (NullPointerException npe) {
                    }

                    if(serial_number==null || "".equals(serial_number)){
                        try {
                            serial_number = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "EMCSerialNumber");
                            if(serial_number!=null && !serial_numbers.contains(serial_number)){
                                serial_numbers.add(serial_number);
                            }
                        } catch (NullPointerException npe) {
                        }
                    }

                    try {
                        part_number = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "PartNumber");
                        if(part_number!=null && !part_numbers.contains(part_number)){
                            part_numbers.add(part_number);
                        }
                    } catch (NullPointerException npe) {
                    }

                    try {
                        version = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "Version");
                        if(version!=null && !versions.contains(version)){
                            versions.add(version);
                        }
                    } catch (NullPointerException npe) {
                    }

                    try {
                        version = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "VersionString");
                        if(version!=null && !versions.contains(version)){
                            versions.add(version);
                        }
                    } catch (NullPointerException npe) {
                    }

                    try {
                        version = cimDT.getCIMInstancePropertyValueString(cim_PhysicalPackageCI, "EMCPromRevision");
                        if(version!=null && !versions.contains(version)){
                            versions.add(version);
                        }
                    } catch (NullPointerException npe) {
                    }
                }
            }

            cs.model = cim_Q.buildStringFromVector(models,",");
            cs.manufacture = cim_Q.buildStringFromVector(manufactures,",");
            cs.part_number = cim_Q.buildStringFromVector(part_numbers, ",");

            if(serial_numbers.size()>0) {
                cs.serial_number = cim_Q.buildStringFromVector(serial_numbers, ",");
            }else{
                cs.serial_number = cs.part_number;
            }
            if(versions.size()>0 && cs.firmware_version==null){
                cs.firmware_version = cim_Q.buildStringFromVector(versions, ",");
            }
        } catch (Exception e) {
            Logger.warn("Physical Package:{}", e);
            Logger.warn("No Physical Package for " + creationClassName);
        }
    }

    private void getSoftwareInstation(AssetModel cs, CIMObjectPath op, CIM_DataTypes cimDT, CIM_Qualifiers cim_Q, String creationClassName){
        try {

          CIMInstance inst = null;
            Vector<String> versions = new Vector<String>();
            Vector<String> names = new Vector<String>();
            Vector<String> manufactures = new Vector<String>();
            String version = "";
            String name = "";
            String manufacture = null;
            CloseableIterator<CIMInstance> softIds = client.associatorInstances(op,
                    "CIM_InstalledSoftwareIdentity", "CIM_SoftwareIdentity",
                    "System", "InstalledSoftware", false,
                    null);
            if(softIds== null || !softIds.hasNext()){
                softIds = client.associatorInstances(op,
                        "CIM_ElementSoftwareIdentity", "CIM_SoftwareIdentity",
                        "Dependent", "Antecedent", false,
                        null);
            }
            while (softIds.hasNext()) {
                inst = softIds.next();
                try {
                    name = cimDT.getCIMInstancePropertyValueString(inst, "Name");
                    if(name!=null && !names.contains(name)){
                        names.add(name);
                    }
                } catch (NullPointerException npe) {


                }

                try{
                    name = cimDT.getCIMInstancePropertyValueString(inst, "EMCOS");
                    if(name!=null && !names.contains(name)){
                        names.add(name);
                    }
                }catch (NullPointerException ne){

                }

                if(name==null || "".equals(name)) {
                    try {
                        name = cimDT.getCIMInstancePropertyValueString(inst, "ElementName");
                        if (name != null && !names.contains(name)) {
                            names.add(name);
                        }
                    } catch (NullPointerException n) {

                    }
                }

                try {
                    version = cimDT.getCIMInstancePropertyValueString(inst, "VersionString");
                    if(version!=null && !versions.contains(version)){
                        versions.add(version);
                    }
                } catch (NullPointerException npe) {
                }

                try {
                    manufacture = cimDT.getCIMInstancePropertyValueString(inst, "Manufacturer");
                    if(manufacture!=null && !manufactures.contains(manufacture)){
                        manufactures.add(manufacture);
                    }
                } catch (NullPointerException npe) {
                }
            }


            if(versions.size()>0 && cs.version==null){
                cs.version = cim_Q.buildStringFromVector(versions, ",");
                if(cs.firmware_version==null || "".equals(cs.firmware_version)){
                    cs.firmware_version = cs.version;
                }
            }

            if(names.size()>0){
                cs.os_name = cim_Q.buildStringFromVector(names, ",");
            }

            if(manufactures.size()>0 && cs.manufacture == null)
                cs.manufacture = cim_Q.buildStringFromVector(manufactures,",");

        } catch (WBEMException e){
            Logger.warn("Software Identity:{}", e);
            Logger.warn("No Software Identity for " + creationClassName);
        }

    }

    public void getController(CIMObjectPath op, CIM_DataTypes cimDT, CIM_Qualifiers cim_Q, String parentName, String creationClassName){
        try {
            CloseableIterator<CIMInstance> instances = client.associatorInstances(op,
                    "CIM_ComponentCS", "CIM_ComputerSystem",
                    "GroupComponent", "PartComponent", false, null);
            CIMInstance inst = null;
            TResController controller = null;
            ObjectMapper om = new ObjectMapper();
            ArrayNode controllerArray = om.createArrayNode();
            List<TResPort> ports = new ArrayList<TResPort>();


            while (instances.hasNext()) {
                inst = instances.next();
                String name = cimDT.getCIMInstancePropertyValueString(inst, "Name");
                String createClassName = cimDT.getCIMInstancePropertyValueString(inst, "CreationClassName");
                if (!inst.getClassName().contains("StorageProcessorSystem")
                        && !inst.getClassName()
                        .contains("StorageLPARSystem")
                        && !inst.getClassName().contains("ONTAP_Node"))
                    continue;

                controller = new TResController();
                controller.name = name;
                controller.creation_class_name = createClassName;

                this.handleComputerSystem(client, inst.getObjectPath(), (AssetModel)controller, inst, cim_Q, cimDT, "", createClassName);

                int memorySize = 0;
                try {
                    memorySize = cimDT.getCIMInstancePropertyUnsignedInt32Value(inst, "MemorySize").intValue();
                } catch (Exception e) {

                }
                if(memorySize==0){
                    try {
                        memorySize = cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "CacheMemorySize").intValue();
                    } catch (Exception e) {

                    }
                }
                if(memorySize==0){
                    try {
                        memorySize = cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "EMCMemorySize").intValue();
                    } catch (Exception e) {

                    }
                }
                int numPorts = 0;
                try {
                    numPorts = cimDT.getCIMInstancePropertyUnsignedInt32Value(inst, "NumPorts").intValue();
                } catch (Exception e) {
                }
                String protocol = null;
                try {
                    protocol = cimDT.getCIMInstancePropertyValueString(inst, "Protocol");
                } catch (Exception e) {
                }
                String[] roles = (String[])null;
                try {
                    roles = cimDT.getStringArrayPropertyValue(inst, "Roles");

                    int rolesSize = 0;
                    if (roles != null) {
                        rolesSize = roles.length;
                    }
                    Vector rolesString = new Vector();
                    for (int x = 0; x < rolesSize; x++) {
                        String rolesfinal = roles[x];
                        rolesString.add(rolesfinal);
                    }

                    String rolesfinalf = cim_Q.buildStringFromVector(rolesString, ",");
                    controller.roles = rolesfinalf;
                } catch (Exception e) {
                }
                int slotNumber = 0;
                try {
                    slotNumber = cimDT.getCIMInstancePropertyUnsignedInt32Value(inst, "SlotNumber").intValue();
                } catch (Exception e) {
                }

                controller.slot = slotNumber;
                controller.memory_size = memorySize;
                controller.num_ports = numPorts;
                controller.system_name = parentName;

                controllerArray.add(Json.toJson(controller));

                this.getPort(ports, inst.getObjectPath(), cimDT, cim_Q, (AssetModel)controller,Constants.DEVICE_ARRAY, Constants.SUBDEVICE_CONTROLLER, createClassName);

                this.getPhysicalPackage(controller, op, cimDT, cim_Q, creationClassName);

                this.getSoftwareInstation(controller, op, cimDT, cim_Q, creationClassName);
            }

            if(ports==null || ports.size()==0){
                this.getPort(ports, inst.getObjectPath(), cimDT, cim_Q, (AssetModel)controller,Constants.DEVICE_ARRAY, null, parentName);
            }

            TResController.createAll(controllerArray);

            TResPort.createAll(Json.toJson(ports));
        } catch (WBEMException e) {
            Logger.info("Get ComponentCS Error:{}", e);
        }
    }

    public void getPort(List<TResPort> ports, CIMObjectPath op, CIM_DataTypes cimDT, CIM_Qualifiers cim_Q, AssetModel am,String device_type, String subdevice_type, String creationClassName){
        try {

            CIMInstance inst = null;
            CloseableIterator<CIMInstance> instances = client.associatorInstances(op,
                    "CIM_SystemDevice", "CIM_FCPort",
                    "GroupComponent", "PartComponent", false,
                    null);

            Logger.info("Getting ports......");
            TResPort port = null;
            while (instances.hasNext()) {
                inst = instances.next();
                String permanentAddress;
                port = new TResPort();
                try {
                    permanentAddress = cimDT.getCIMInstancePropertyValueString(inst, "PermanentAddress");
                }
                catch (Exception e2)
                {
                    permanentAddress = null;
                }

                port.permanent_address = permanentAddress;
                port.name = permanentAddress;

                String systemCreationClassNamePort;
                try {
                    systemCreationClassNamePort = cimDT.getCIMInstancePropertyValueString(inst, "SystemCreationClassName");
                }
                catch (Exception e2)
                {
                    systemCreationClassNamePort = null;
                }

                String systemNamePort;
                try {
                    systemNamePort = cimDT.getCIMInstancePropertyValueString(inst, "SystemName");
                }
                catch (Exception e2)
                {
                    systemNamePort = null;
                }
                port.system_name = systemNamePort;

                String creationClassNamePort;
                try {
                    creationClassNamePort = cimDT.getCIMInstancePropertyValueString(inst, "CreationClassName");
                }
                catch (Exception e2)
                {
                    creationClassNamePort = null;
                }
                port.creation_class_name = creationClassNamePort;


                String deviceIdPort;
                try {
                    deviceIdPort = cimDT.getCIMInstancePropertyValueString(inst, "DeviceID");
                }
                catch (Exception e2)
                {
                    deviceIdPort = null;
                }
                port.device_id = deviceIdPort;

                String elementNamePort;
                try {
                    elementNamePort = cimDT.getCIMInstancePropertyValueString(inst, "ElementName");
                }
                catch (Exception e2)
                {
                    elementNamePort = null;
                }
                port.element_name = elementNamePort;

                if(port.element_name!=null && !"".equals(port.element_name)){
                    port.display_name = port.element_name;
                } else {
                    port.display_name = port.name;
                }

                int usageRestrictionPort;
                try
                {
                    usageRestrictionPort = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "UsageRestriction").intValue();
                }
                catch (RuntimeException e1)
                {
                    usageRestrictionPort = 0;
                }
                String usageRestrictionConversion = cim_Q.usageRestriction(usageRestrictionPort);

                port.usage_restriction = usageRestrictionConversion;

                try
                {
                    UnsignedInteger16[] operationalStatusPort = cimDT.getUint16ArrayPropertyValue(inst, "OperationalStatus");
                    int operationalStatusSize = 0;
                    if (operationalStatusPort != null) {
                        operationalStatusSize = operationalStatusPort.length;
                    }
                    Logger.debug("operationalStatusSize = " + operationalStatusSize);
                    Vector portOperationalStatusString = new Vector();
                    for (int x = 0; x < operationalStatusSize; x++)
                    {
                        UnsignedInteger16 opsInt = operationalStatusPort[x];
                        int operationalStatusInt = Integer.parseInt(opsInt.toString());

                        String operationalStatusValue = cim_Q.portOperationalStatus(operationalStatusInt);

                        portOperationalStatusString.add(operationalStatusValue);
                    }

                    String portOperationalStatusFinal = cim_Q.buildStringFromVector(portOperationalStatusString, ",");
                    port.operational_status = portOperationalStatusFinal;
                } catch (Exception e) {

                }

                Long speedPort = null;
                try {
                    speedPort = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "Speed").longValue());
                } catch (Exception localException4) {
                }

                port.speed = speedPort;

                Long maxSpeedPort = null;
                try {
                    maxSpeedPort = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "MaxSpeed").longValue());
                } catch (Exception localException5) {

                }
                port.max_speed = maxSpeedPort;

                int portTypePort = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "PortType").intValue();
                String portTypeConversion = cim_Q.portType(portTypePort);

                port.port_type = portTypeConversion;

                int linkTechnologyPort;
                try {
                    linkTechnologyPort = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "LinkTechnology").intValue();
                }
                catch (Exception e)
                {
                    linkTechnologyPort = 555;
                }
                Long supportedMaximumTransmissionUnit = null;
                try {
                    supportedMaximumTransmissionUnit = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "SupportedMaximumTransmissionUnit").longValue());
                } catch (Exception localException6) {
                }
                Logger.debug(this.CN + " FCPort WWN = " + permanentAddress);

                String linkTechnologyConversion = cim_Q.linkTechnology(linkTechnologyPort);
                if (linkTechnologyPort != 555) {
                    port.link_technology = linkTechnologyConversion;
                }
                port.supported_maximum_transmission_unit = supportedMaximumTransmissionUnit;

                port.system_name = am.system_name;
                port.device_type = device_type;
                if(subdevice_type!=null) {
                    port.subsystem_name = am.name;
                    port.subdevice_type = subdevice_type;
                }

                if(port.status==null){
                    port.status = port.operational_status;
                }

                if(port.status_description == null){
                    port.status_description = port.operational_status;
                }

                ports.add(port);
            }

        } catch (WBEMException e){
            Logger.warn("GET Port:{}", e);
            Logger.warn("GET Port for " + creationClassName);
        }
    }

    public void getStoragePool(CIMObjectPath op,CIM_DataTypes cimDT, CIM_Qualifiers cim_Q,  String parentName, String creationClassName){
        try{
            CloseableIterator<CIMInstance> instances = client.associatorInstances(op,
                    "CIM_HostedStoragePool", "CIM_StoragePool",
                    "GroupComponent", "PartComponent", false, null);
            CIMInstance inst = null;
            TResStoragePool pool = null;
            List<TResStoragePool> pools = new ArrayList<TResStoragePool>();
            while(instances.hasNext()){
                inst = instances.next();
                pool = new TResStoragePool();

                try {
                    String elementName = cimDT.getCIMInstancePropertyValueString(inst, "ElementName");
                    pool.element_name = elementName;
                } catch (Exception e){

                }

                try {
                    String name = cimDT.getCIMInstancePropertyValueString(inst, "Name");
                    String nameFormat = cimDT.getCIMInstancePropertyValueString(inst, "NameFormat");
                    pool.name = name;
                    pool.name_format = nameFormat;
                }catch (Exception e){

                }

                if(pool.element_name!=null && !"".equals(pool.element_name)){
                    pool.display_name = pool.element_name;
                } else {
                    pool.display_name = pool.name;
                }

                try {
                    String instanceID = cimDT.getCIMInstancePropertyValueString(inst, "InstanceID");
                    pool.instance_id = instanceID;
                } catch (Exception e){

                }

                try {
                    String poolID = cimDT.getCIMInstancePropertyValueString(inst, "PoolID");
                    pool.pool_id = poolID;
                } catch (Exception e){

                }

                try {
                    String raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "RAIDLevel");
                    pool.raid_level = raidLevel;
                } catch (Exception e){

                }

                try {
                    Boolean primordial = cimDT.getCIMInstancePropertyBooleanValue(inst, "Primordial");
                    pool.primordial = primordial;
                } catch (Exception e){

                }

                Long totalManagedSpace = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "TotalManagedSpace").longValue());
                Long remainingManagedSpace = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "RemainingManagedSpace").longValue());

                pool.total_managed_space = totalManagedSpace;
                pool.capacity = pool.total_managed_space;
                pool.remaining_managed_space = remainingManagedSpace;
                pool.free_capacity = pool.remaining_managed_space;

                pool.system_name = parentName;

                try
                {
                    UnsignedInteger16[] operationalStatusPort = cimDT.getUint16ArrayPropertyValue(inst, "OperationalStatus");
                    int operationalStatusSize = 0;
                    if (operationalStatusPort != null) {
                        operationalStatusSize = operationalStatusPort.length;
                    }
                    Logger.debug("operationalStatusSize = " + operationalStatusSize);
                    Vector portOperationalStatusString = new Vector();
                    for (int x = 0; x < operationalStatusSize; x++)
                    {
                        UnsignedInteger16 opsInt = operationalStatusPort[x];
                        int operationalStatusInt = Integer.parseInt(opsInt.toString());

                        String operationalStatusValue = cim_Q.portOperationalStatus(operationalStatusInt);

                        portOperationalStatusString.add(operationalStatusValue);
                    }

                    String portOperationalStatusFinal = cim_Q.buildStringFromVector(portOperationalStatusString, ",");
                    pool.operational_status = portOperationalStatusFinal;

                    pools.add(pool);
                } catch (Exception e) {

                }

                if(pool.status==null){
                    pool.status = pool.operational_status;
                }

                if(pool.status_description == null){
                    pool.status_description = pool.operational_status;
                }

                this.getStorageVolume(inst.getObjectPath(), cimDT, cim_Q, parentName, pool.name, pool.instance_id, creationClassName);
            }

            TResStoragePool.createAll(Json.toJson(pools));

        } catch (WBEMException e){
            Logger.warn("GET StoragePool:{}", e);
            Logger.warn("GET StoragePool for " + creationClassName);
        }
    }


    public void getStorageVolume(CIMObjectPath op,CIM_DataTypes cimDT, CIM_Qualifiers cim_Q,  String parentName, String poolName, String poolInstance, String creationClassName){
        try{
            CloseableIterator<CIMInstance> instances = client.associatorInstances(op,
                    "CIM_AllocatedFromStoragePool", "CIM_StorageVolume",
                    "Antecedent", "Dependent", false, null);
            CIMInstance inst = null;
            TResStorageVolume vol = null;
            List<TResStorageVolume> vols = new ArrayList<TResStorageVolume>();
            while(instances.hasNext()){
                inst = instances.next();
                vol = new TResStorageVolume();

                try {
                    String deviceID = cimDT.getCIMInstancePropertyValueString(inst, "DeviceID");
                    vol.device_id = deviceID;
                } catch (Exception e){

                }

                try {
                    String elementName = cimDT.getCIMInstancePropertyValueString(inst, "ElementName");
                    vol.element_name = elementName;
                } catch (Exception e){

                }

                try {
                    String name = cimDT.getCIMInstancePropertyValueString(inst, "Name");
                    vol.name = name;
                }catch (Exception e){

                }

                try {
                    String nameFormat = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "NameFormat").toString();
                    vol.name_format = nameFormat;
                }catch (Exception e){

                }

                if(vol.element_name!=null && !"".equals(vol.element_name)){
                    vol.display_name = vol.element_name;
                } else {
                    vol.display_name = vol.name;
                }

                String instanceID = null;
                try {
                    instanceID = cimDT.getCIMInstancePropertyValueString(inst, "InstanceID");
                } catch (Exception e){

                }

                if(instanceID == null){
                    try {
                        instanceID = cimDT.getCIMInstancePropertyValueString(inst, "EMCWWN");
                    } catch (Exception e){

                    }
                }

                if(instanceID==null){
                    instanceID = vol.device_id;
                }

                vol.instance_id = instanceID;

                try {
                    String poolID = cimDT.getCIMInstancePropertyValueString(inst, "PoolID");
                    vol.pool_id = poolID;
                } catch (Exception e){

                }

                vol.pool_name = poolName;

                vol.pool_instance = poolInstance;

                String raidLevel = null;
                try {
                    raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "RAIDLevel");

                } catch (Exception e){

                }

                if(raidLevel==null) {
                    try {
                        raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "EMCRaidLevel");

                    } catch (Exception e) {

                    }
                }

                if(raidLevel==null) {
                    try {
                        raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "ErrorMethodology");

                    } catch (Exception e) {

                    }
                }
                vol.raid_level = raidLevel;

                try {
                    Boolean seqentialAccess = cimDT.getCIMInstancePropertyBooleanValue(inst, "SeqentialAccess");
                    vol.segential_access = seqentialAccess;
                } catch (Exception e){

                }

                try {
                    Boolean primordial = cimDT.getCIMInstancePropertyBooleanValue(inst, "Primordial");
                    vol.primordial = primordial;
                } catch (Exception e){

                }

                try {
                    int access = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "Access").intValue();
                    vol.access = access;
                } catch (Exception e){

                }

                try {
                    Boolean isBasedOnUnderlyingRedundancy = cimDT.getCIMInstancePropertyBooleanValue(inst, "IsBasedOnUnderlyingRedundancy");
                    vol.is_underlying_redundancy = isBasedOnUnderlyingRedundancy;
                } catch (Exception e){

                }

                try {
                    Boolean thinlyProvisioned = cimDT.getCIMInstancePropertyBooleanValue(inst, "ThinlyProvisioned");
                    vol.is_thin_provisioned = thinlyProvisioned;
                } catch (Exception e){

                }

                Boolean isAssigned = false;
                try {
                    isAssigned = cimDT.getCIMInstancePropertyBooleanValue(inst, "isAssigned");

                } catch (Exception e){

                }

                try {
                    isAssigned = cimDT.getCIMInstancePropertyBooleanValue(inst, "EMCIsMapped");

                } catch (Exception e){

                }
                vol.is_mapped = isAssigned;


                String controllerName = null;
                try {
                    controllerName = cimDT.getCIMInstancePropertyValueString(inst, "PreferredManager");
                } catch (Exception e){

                }

                if(controllerName==null){
                    try {
                        controllerName = cimDT.getCIMInstancePropertyValueString(inst, "EMCCurrentOwningStorageProcessor");
                    } catch (Exception e){

                    }
                }
                vol.controller_name = controllerName;


                Long blockSize = new Long(512);
                try {
                    blockSize = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "BlockSize").longValue());
                } catch (Exception e){

                }
                Long consumableBlocks = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "ConsumableBlocks").longValue());
                Long numberOfBlocks = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "NumberOfBlocks").longValue());

                vol.block_size = blockSize;
                vol.consumable_blocks = consumableBlocks;
                vol.number_blocks = numberOfBlocks;

                vol.total_managed_space = vol.block_size*vol.number_blocks/1024;
                vol.capacity = vol.total_managed_space;
                vol.remaining_managed_space = vol.block_size*vol.number_blocks/1024 - vol.block_size*vol.consumable_blocks/1024;
                vol.used_capacity = vol.block_size*vol.consumable_blocks/1024;
                vol.free_capacity = vol.remaining_managed_space;

                int deltaReservation = 0;
                try {
                    deltaReservation = cimDT.getCIMInstancePropertyUnsignedInt8Value(inst, "DeltaReservation").intValue();
                } catch (Exception e) {
                }

                int packageRedundancy = 0;
                try {
                    packageRedundancy = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "PackageRedundancy").intValue();
                } catch (Exception e) {
                }

                int dataRedundancy = 0;
                try {
                    dataRedundancy = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "DataRedundancy").intValue();
                } catch (Exception e) {
                }
                boolean noSinglePointOfFailure;
                try {
                    noSinglePointOfFailure = cimDT.getCIMInstancePropertyBooleanValue(inst, "NoSinglePointOfFailure").booleanValue();
                }
                catch (Exception e)
                {
                    noSinglePointOfFailure = false;
                }

                vol.data_redundancy = dataRedundancy;
                vol.package_redundancy = packageRedundancy;
                vol.delta_reservation = deltaReservation;

                try
                {
                    UnsignedInteger16[] extentStatus = cimDT.getUint16ArrayPropertyValue(inst, "ExtentStatus");
                    int extentStatusSize = 0;
                    if (extentStatus != null) {
                        extentStatusSize = extentStatus.length;
                    }
                    Vector extentStatusString = new Vector();
                    for (int x = 0; x < extentStatusSize; x++)
                    {
                        int extentStatusInt = Integer.parseInt(extentStatus[x].toString());

                        String extentStatusValue = cim_Q.extentStatus(extentStatusInt);

                        extentStatusString.add(extentStatusValue);
                    }

                    String extentStatusFinal = cim_Q.buildStringFromVector(extentStatusString, ",");
                    vol.extent_status = extentStatusFinal;
                } catch (Exception e) {

                }

                vol.system_name = parentName;

                int requestedState = 0;
                try {
                    requestedState = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "RequestedState").intValue();
                } catch (RuntimeException e) {
                }

                int enabledState = 0;
                try {
                    enabledState = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "EnabledState").intValue();
                } catch (RuntimeException e) {
                }

                int healthState = 0;
                try {
                    healthState = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "HealthState").intValue();
                } catch (RuntimeException e) {
                }


                try
                {
                    UnsignedInteger16[] operationalStatusPort = cimDT.getUint16ArrayPropertyValue(inst, "OperationalStatus");
                    int operationalStatusSize = 0;
                    if (operationalStatusPort != null) {
                        operationalStatusSize = operationalStatusPort.length;
                    }
                    Logger.debug("operationalStatusSize = " + operationalStatusSize);
                    Vector portOperationalStatusString = new Vector();
                    for (int x = 0; x < operationalStatusSize; x++)
                    {
                        UnsignedInteger16 opsInt = operationalStatusPort[x];
                        int operationalStatusInt = Integer.parseInt(opsInt.toString());

                        String operationalStatusValue = cim_Q.portOperationalStatus(operationalStatusInt);

                        portOperationalStatusString.add(operationalStatusValue);
                    }

                    String portOperationalStatusFinal = cim_Q.buildStringFromVector(portOperationalStatusString, ",");
                    vol.operational_status = portOperationalStatusFinal;

                    vols.add(vol);
                } catch (Exception e) {

                }

                if(vol.status==null){
                    vol.status = vol.operational_status;
                }

                if(vol.status_description == null){
                    vol.status_description = vol.operational_status;
                }

                vols.add(vol);
            }

            TResStorageVolume.createAll(Json.toJson(vols));

        } catch (WBEMException e){
            Logger.warn("GET StoragePool:{}", e);
            Logger.warn("GET StoragePool for " + creationClassName);
        }
    }

    public void getDiskDrive(CIMObjectPath op,CIM_DataTypes cimDT, CIM_Qualifiers cim_Q,  String parentName, String creationClassName){
        try{
            CloseableIterator<CIMInstance> instances = client.associatorInstances(op,
                    "CIM_SystemDevice", "CIM_DiskDrive",
                    "GroupComponent", "PartComponent", false, null);
            CIMInstance inst = null;
            TResDiskDrive disk = null;
            List<TResDiskDrive> disks = new ArrayList<TResDiskDrive>();
            while(instances.hasNext()){
                inst = instances.next();
                disk = new TResDiskDrive();

                try {
                    String deviceID = cimDT.getCIMInstancePropertyValueString(inst, "DeviceID");
                    disk.device_id = deviceID;
                } catch (Exception e){

                }

                try {
                    String elementName = cimDT.getCIMInstancePropertyValueString(inst, "ElementName");
                    disk.element_name = elementName;
                } catch (Exception e){

                }

                try {
                    String name = cimDT.getCIMInstancePropertyValueString(inst, "Name");
                    disk.name = name;
                }catch (Exception e){

                }

                try {
                    String nameFormat = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "NameFormat").toString();
                    disk.name_format = nameFormat;
                }catch (Exception e){

                }

                try {
                    String caption = cimDT.getCIMInstancePropertyValueString(inst, "Caption");
                    disk.slot = caption;
                    if(caption!=null && caption.toLowerCase().contains("fc")||caption.toLowerCase().contains("sas")){
                        disk.disk_type = caption;
                    }
                }catch (Exception e){

                }

                if(disk.element_name!=null && !"".equals(disk.element_name)){
                    disk.display_name = disk.element_name;
                } else {
                    disk.display_name = disk.name;
                }

                String instanceID = null;
                try {
                    instanceID = cimDT.getCIMInstancePropertyValueString(inst, "InstanceID");
                } catch (Exception e){

                }

                if(instanceID == null){
                    try {
                        instanceID = cimDT.getCIMInstancePropertyValueString(inst, "EMCWWN");
                    } catch (Exception e){

                    }
                }

                if(instanceID==null){
                    instanceID = disk.device_id;
                }

                disk.instance_id = instanceID;

                String raidLevel = null;
                try {
                    raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "RAIDLevel");

                } catch (Exception e){

                }

                if(raidLevel==null) {
                    try {
                        raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "EMCRaidLevel");

                    } catch (Exception e) {

                    }
                }

                if(raidLevel==null) {
                    try {
                        raidLevel = cimDT.getCIMInstancePropertyValueString(inst, "ErrorMethodology");

                    } catch (Exception e) {

                    }
                }
                disk.raid_level = raidLevel;

                try {
                    Boolean seqentialAccess = cimDT.getCIMInstancePropertyBooleanValue(inst, "SeqentialAccess");
                    disk.segential_access = seqentialAccess;
                } catch (Exception e){

                }

                try {
                    Boolean primordial = cimDT.getCIMInstancePropertyBooleanValue(inst, "Primordial");
                    disk.primordial = primordial;
                } catch (Exception e){

                }


                int deltaReservation = 0;
                try {
                    deltaReservation = cimDT.getCIMInstancePropertyUnsignedInt8Value(inst, "DeltaReservation").intValue();
                } catch (Exception e) {
                }

                int packageRedundancy = 0;
                try {
                    packageRedundancy = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "PackageRedundancy").intValue();
                } catch (Exception e) {
                }

                int dataRedundancy = 0;
                try {
                    dataRedundancy = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "DataRedundancy").intValue();
                } catch (Exception e) {
                }


                disk.data_redundancy = dataRedundancy;
                disk.package_redundancy = packageRedundancy;
                disk.delta_reservation = deltaReservation;

                this.getPhysicalPackage(disk, inst.getObjectPath(), cimDT, cim_Q, creationClassName);
                this.getSoftwareInstation(disk, inst.getObjectPath(), cimDT, cim_Q, creationClassName);
                this.getStorageExtent(disk, inst.getObjectPath(), cimDT, cim_Q, creationClassName);


                try
                {
                    UnsignedInteger16[] extentStatus = cimDT.getUint16ArrayPropertyValue(inst, "ExtentStatus");
                    int extentStatusSize = 0;
                    if (extentStatus != null) {
                        extentStatusSize = extentStatus.length;
                    }
                    Vector extentStatusString = new Vector();
                    for (int x = 0; x < extentStatusSize; x++)
                    {
                        int extentStatusInt = Integer.parseInt(extentStatus[x].toString());

                        String extentStatusValue = cim_Q.extentStatus(extentStatusInt);

                        extentStatusString.add(extentStatusValue);
                    }

                    String extentStatusFinal = cim_Q.buildStringFromVector(extentStatusString, ",");
                    disk.extent_status = extentStatusFinal;
                } catch (Exception e) {

                }

                disk.system_name = parentName;

                int requestedState = 0;
                try {
                    requestedState = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "RequestedState").intValue();
                } catch (RuntimeException e) {
                }

                int enabledState = 0;
                try {
                    enabledState = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "EnabledState").intValue();
                } catch (RuntimeException e) {
                }

                int healthState = 0;
                try {
                    healthState = cimDT.getCIMInstancePropertyUnsignedInt16Value(inst, "HealthState").intValue();
                } catch (RuntimeException e) {
                }


                try
                {
                    UnsignedInteger16[] operationalStatusPort = cimDT.getUint16ArrayPropertyValue(inst, "OperationalStatus");
                    int operationalStatusSize = 0;
                    if (operationalStatusPort != null) {
                        operationalStatusSize = operationalStatusPort.length;
                    }
                    Logger.debug("operationalStatusSize = " + operationalStatusSize);
                    Vector portOperationalStatusString = new Vector();
                    for (int x = 0; x < operationalStatusSize; x++)
                    {
                        UnsignedInteger16 opsInt = operationalStatusPort[x];
                        int operationalStatusInt = Integer.parseInt(opsInt.toString());

                        String operationalStatusValue = cim_Q.portOperationalStatus(operationalStatusInt);

                        portOperationalStatusString.add(operationalStatusValue);
                    }

                    String portOperationalStatusFinal = cim_Q.buildStringFromVector(portOperationalStatusString, ",");
                    disk.operational_status = portOperationalStatusFinal;

                    disks.add(disk);
                } catch (Exception e) {

                }

                if(disk.status==null){
                    disk.status = disk.operational_status;
                }

                if(disk.status_description == null){
                    disk.status_description = disk.operational_status;
                }

                disks.add(disk);
            }

            TResDiskDrive.createAll(Json.toJson(disks));

        } catch (WBEMException e){
            Logger.warn("GET StoragePool:{}", e);
            Logger.warn("GET StoragePool for " + creationClassName);
        }
    }

    private void getStorageExtent(TResDiskDrive disk, CIMObjectPath op, CIM_DataTypes cimDT, CIM_Qualifiers cim_Q, String creationClassName){
        try {

            CIMInstance inst = null;
            CloseableIterator<CIMInstance> instances = client.associatorInstances(op,
                    "CIM_MediaPresent", "CIM_StorageExtent",
                    "Antecedent", "Dependent", false,
                    null);
            CloseableIterator<CIMInstance> subInstances = null;
            while (instances.hasNext()) {
                inst = instances.next();
                Long blockSize = new Long(512);
                try {
                    blockSize = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "BlockSize").longValue());
                } catch (Exception e){

                }
                disk.block_size = blockSize;

                try {
                    Long numberOfBlocks = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "NumberOfBlocks").longValue());
                    disk.number_blocks = numberOfBlocks;
                    disk.total_managed_space = disk.block_size*disk.number_blocks/1024;
                    disk.capacity = disk.total_managed_space;
                } catch (RuntimeException e){

                }
                try {
                    Long consumableBlocks = Long.valueOf(cimDT.getCIMInstancePropertyUnsignedInt64Value(inst, "ConsumableBlocks").longValue());
                    disk.consumable_blocks = consumableBlocks;
                    disk.used_capacity = disk.block_size*disk.consumable_blocks/1024;
                    disk.remaining_managed_space = disk.capacity - disk.used_capacity;
                    disk.free_capacity = disk.remaining_managed_space;
                } catch (Exception e){

                }

                if(disk.slot==null || "".equals(disk.slot)){
                    try {
                        String caption = cimDT.getCIMInstancePropertyValueString(inst, "Caption");
                        disk.slot = caption;
                        if(caption!=null && caption.toLowerCase().contains("fc")||caption.toLowerCase().contains("sas")){
                            disk.disk_type = caption;
                        }
                    }catch (Exception e){

                    }
                }

                subInstances = client.associatorInstances(op,
                        "CIM_IsSpare", "CIM_RedundancySet",
                        "Antecedent", "Dependent", false,
                        null);
                if(subInstances!=null && subInstances.hasNext()){
                    disk.is_spare = true;
                } else {
                    disk.is_spare = false;
                }

                try
                {
                    UnsignedInteger16[] extentStatusArray = cimDT.getUint16ArrayPropertyValue(inst, "ExtentStatus");
                    int extentStatusSize = 0;
                    if (extentStatusArray != null) {
                        extentStatusSize = extentStatusArray.length;
                    }
                    Vector<Integer> _v_extent = new Vector<Integer>();
                    for (int x = 0; x < extentStatusSize; x++)
                    {
                        UnsignedInteger16 opsInt = extentStatusArray[x];
                        int extentStatusInt = Integer.parseInt(opsInt.toString());

                        if(extentStatusInt==13){
                            disk.is_spare = true;
                        }

                    }

                    disk.extent_status = cim_Q.buildStringFromVector(_v_extent,",");

                } catch (Exception e) {

                }
            }

        } catch (WBEMException e){
            Logger.warn("Storage Extent:{}", e);
            Logger.warn("No Storage Extent for " + creationClassName);
        }

    }

    public static void main(String[] args){

        CIMCollector collector = new CIMCollector();
        collector.connect("http","10.1.11.35","5988","root/brocade1","admin","password");
    }

}
