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
    requires jersey.container.servlet.core;

    opens eus.ehu.ridesfx.domain to javafx.base, org.hibernate.orm.core;
    opens eus.ehu.ridesfx.ui to javafx.fxml;
    opens eus.ehu.ridesfx.uicontrollers to javafx.fxml;
    opens eus.ehu.ridesfx.businessLogic to jersey.container.servlet.core;


    exports eus.ehu.ridesfx.ui;
    exports eus.ehu.ridesfx.businessLogic;
}
