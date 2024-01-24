package eus.ehu.ridesfx.businessLogic;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class BusinessLogicServer {

    public static void main(String[] args) throws Exception {
        // Create a basic jetty server object without declaring the port.
        // Since we are configuring the server programmatically, we'll be setting ports in the configuration.
        Server server = new Server(8080);

        // The ServletContextHandler is a full-featured handler that is good for developing web apps
        // but it is also a servlet handler and can be used for jersey.
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Add our servlet - Jersey manages the servlet through the ServletContainer.
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                BlFacadeImplementation.class.getCanonicalName());

        // Add the servlet context handler to the server.
        server.setHandler(context);

        // Start the server
        server.start();
        server.join();
    }
}
