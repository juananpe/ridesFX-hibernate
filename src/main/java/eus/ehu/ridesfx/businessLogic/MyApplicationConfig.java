package eus.ehu.ridesfx.businessLogic;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig() {
        // Register an instance of BlFacadeImplementation
        register(new BlFacadeImplementation());
    }
}