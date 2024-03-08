package eus.ehu.ridesfx.cli;
import com.google.gson.Gson;
import eus.ehu.ridesfx.domain.Driver;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final String BASE_URL = "http://localhost:8888/api/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static void main(String[] args) {
        try {
            Driver newDriver = new Driver("12345", "John Doe");
            setCurrentDriver(newDriver);

            Driver currentDriver = getCurrentDriver();
            System.out.println("Current Driver: " + currentDriver.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setCurrentDriver(Driver driver) throws Exception {
        String url = BASE_URL + "setCurrentDriver";
        String json = gson.toJson(driver);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            System.out.println("Driver set successfully");
        }
    }

    private static Driver getCurrentDriver() throws Exception {
        String url = BASE_URL + "getCurrentDriver";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            System.out.println("Response: " + response);
            return gson.fromJson(responseBody, Driver.class);
        }
    }
}
