package eus.ehu.ridesfx.businessLogic;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class BusinessLogicServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8888);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "javax.ws.rs.Application",
                MyApplicationConfig.class.getCanonicalName());

        server.setHandler(context);

        server.start();
        server.join();
    }

}
