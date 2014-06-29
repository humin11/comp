package discovery.host.vmware;


import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.*;
import play.Logger;
import play.libs.Json;

import java.net.URL;
import java.rmi.RemoteException;

/**
 * Created by Humin on 4/30/14.
 */
public class DiscoveryVMWare {



    public static void getHost() {
        try {
//            ServiceInstance si = new ServiceInstance(new URL(url), user, psw,
//                    true);
			ServiceInstance si = new ServiceInstance(new URL("https://192.168.0.220:443/sdk"), "root", "vmware@01", true);

            if (si != null) {
               Folder _root = si.getRootFolder();
               ManagedEntity[] mes = _root.getChildEntity();
               for(ManagedEntity me: mes){
                   Datacenter dc = (Datacenter)me;
                   Logger.info(dc.getName());
                   getHost(dc);
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getHost(Datacenter dc){
        try {
            Folder _folder = dc.getHostFolder();
            ManagedEntity[] mes = _folder.getChildEntity();
            for(ManagedEntity me: mes){
                ComputeResource cr = (ComputeResource)me;
                Logger.info(Json.toJson(cr.getHosts()[0].getConfig()).toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void getVM(Datacenter dc){
        try {
            Folder _folder = dc.getVmFolder();
            ManagedEntity[] mes = _folder.getChildEntity();
            for(ManagedEntity me: mes){
                VirtualMachine vm = (VirtualMachine)me;
                VirtualMachineConfigInfo vmif = vm.getConfig();
                long[] nwwns = vmif.getNpivNodeWorldWideName();
                if(nwwns!= null && nwwns.length>0){
                    for(long nwwn: nwwns){

                        Logger.info(Json.toJson(Long.toHexString(nwwn).toUpperCase()).toString());
                    }
                }

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
