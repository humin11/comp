package controllers;

import discovery.DiscoverySMI;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        String result = "";
//        DiscoveryVMWare vm = new DiscoveryVMWare();
//
//        DiscoveryVMWare.getHost();


        //DiscoverySMI cs = new DiscoverySMI("http","10.1.11.35","5998","root/lsissi11","admin","admin");
//        cs = new DiscoveryComputerSystem("http","10.1.11.35","5988","root/brocade1","admin","password");
        return ok(index.render(result));
    }

    public static Result dashboard() {
        return ok(dashboard.render());
    }

    public static Result configuration() {
        return ok(configuration.render());
    }

}
