module eus.ehu.sharetrip {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires jakarta.persistence;
    requires java.ws.rs;
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.servlet;
    requires com.google.gson;
    requires okhttp3;

    requires jersey.common;
    requires jersey.server;
    requires jersey.container.servlet.core;


    opens eus.ehu.ridesfx.domain to javafx.base, org.hibernate.orm.core, com.google.gson;
    opens eus.ehu.ridesfx.ui to javafx.fxml;
    opens eus.ehu.ridesfx.uicontrollers to javafx.fxml;
    opens eus.ehu.ridesfx.businessLogic to jersey.container.servlet.core, jersey.server, jersey.common;

    exports eus.ehu.ridesfx.ui;
    exports eus.ehu.ridesfx.businessLogic;
    exports eus.ehu.ridesfx.domain;

}
