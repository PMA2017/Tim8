package takeyourseat.data.remote;

/**
 * Created by Nenad on 5/9/2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://takeyourseat.somee.com/";

    public static ApiService getApiService() {
        return RestClient.getClient(BASE_URL).create(ApiService.class);
    }

}
