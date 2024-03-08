package eus.ehu.ridesfx.businessLogic;

import eus.ehu.ridesfx.configuration.Config;
import eus.ehu.ridesfx.dataAccess.DataAccess;
import eus.ehu.ridesfx.domain.Driver;
import eus.ehu.ridesfx.domain.Ride;
import eus.ehu.ridesfx.exceptions.RideAlreadyExistException;
import eus.ehu.ridesfx.exceptions.RideMustBeLaterThanTodayException;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Implements the business logic as a web service.
 */
@Path("/api")
public class BlFacadeImplementation implements BlFacade {
    DataAccess dbManager;
    Config config = Config.getInstance();
    private Driver currentDriver;

    public BlFacadeImplementation() {
        System.out.println("Creating BlFacadeImplementation instance");
        boolean initialize = config.getDataBaseOpenMode().equals("initialize");
        dbManager = new DataAccess(initialize);
        if (initialize)
            dbManager.initializeDB();
        dbManager.close();
    }

    public BlFacadeImplementation(DataAccess dam) {
        System.out.println("Creating BlFacadeImplementation instance with DataAccess parameter");
        if (config.getDataBaseOpenMode().equals("initialize")) {
            dam.open(true);
            dam.initializeDB();
            dam.close();
        }
        dbManager = dam;
    }


    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

        dbManager.open(false);
        Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverEmail);
        dbManager.close();
        return ride;
    }

    /**
     * This method retrieves the rides from two locations on a given date
     *
     * @param origin      the origin location of a ride
     * @param destination the destination location of a ride
     * @param date        the date of the ride
     * @return collection of rides
     */
    @GET
    @Path("/getRides")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Ride> getRides(@QueryParam("origin") String origin, @QueryParam("destination") String destination, @QueryParam("date") Date date) {
        dbManager.open(false);
        List<Ride> events = dbManager.getRides(origin, destination, date);
        dbManager.close();
        return events;
    }


    @Override
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
        dbManager.open(false);
        List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
        dbManager.close();
        return dates;
    }

    /**
     * This method invokes the data access to retrieve the dates a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */
    @Override
    public Vector<Date> getEventsMonth(Date date) {
        dbManager.open(false);
        Vector<Date> dates = dbManager.getEventsMonth(date);
        dbManager.close();
        return dates;
    }

    // create a jersey endpoint
    @POST
    @Path("/setCurrentDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void setCurrentDriver(Driver driver) {
        this.currentDriver = driver;
        System.out.println("Driver set: " + driver.getName() + " with Email: " + driver.getEmail());
    }

    /**
     * This method returns the current driver
     *
     * @return the current driver
     */
    @GET
    @Path("/getCurrentDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Driver getCurrentDriver() {
        System.out.println("Returning current driver: " + this.currentDriver.getName());
        return this.currentDriver;
    }

    public void close() {
        dbManager.close();
    }

    /**
     * This method invokes the data access to initialize the database with some events and questions.
     * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
     */

    public void initializeBD() {
        dbManager.open(false);
        dbManager.initializeDB();
        dbManager.close();
    }


    /**
     * This method returns all the cities where rides depart
     *
     * @return collection of cities
     */
    @GET
    @Path("/getDepartCities")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<String> getDepartCities() {
        dbManager.open(false);

        List<String> departLocations = dbManager.getDepartCities();

        dbManager.close();

        return departLocations;

    }

    /**
     * Retrieves a list of destination cities available from a specified city.
     *
     * <p>This method accesses the database to find all cities that can be reached directly
     * from the given 'from' city. It uses {@link DataAccess} to handle database operations.</p>
     *
     * @param from The city from which the destination cities are to be found.
     *             It's passed as a query parameter in the GET request.
     * @return A list of strings, each representing a destination city name.
     *         The list is returned as a JSON array.
     */
	@GET
	@Path("/getDestinationCities")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<String> getDestinationCities(@QueryParam("from") String from) {
        dbManager.open(false);
        List<String> targetCities = dbManager.getArrivalCities(from);
        dbManager.close();

        return targetCities;
    }

	/**
	 * Retrieves a list of dates when rides are available between specified cities.
	 *
	 * <p>This method queries the database to find all available ride dates between the 'from' and 'to' cities.
	 * It uses {@link DataAccess} to perform the database operations.</p>
	 *
	 * @param from The starting city of the rides. Passed as a query parameter.
	 * @param to   The destination city of the rides. Passed as a query parameter.
	 * @return A list of {@link Date} objects, each representing an available ride date.
	 *         The list is returned as a JSON array.
	 */
	@Override
	@GET
	@Path("/getDatesWithRides")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Date> getDatesWithRides(@QueryParam("from") String from, @QueryParam("to") String to) {
        dbManager.open(false);
        List<Date> dates = dbManager.getDatesWithRides(from, to);
        dbManager.close();
        return dates;
    }

}
