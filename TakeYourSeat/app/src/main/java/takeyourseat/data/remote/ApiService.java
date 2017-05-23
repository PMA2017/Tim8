package takeyourseat.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import takeyourseat.beans.Restaurant;
import takeyourseat.beans.User;

/**
 * Created by Nenad on 5/9/2017.
 */

public interface ApiService {

    @GET("/api/data/getall/user")
    Call<List<User>> getAllUsers();

    @GET("api/data/getby/user/email/{value}")
    Call<List<User>> getUserByEmail(@Path("value") String email);

    @GET("api/data/getall/restaurant")
    Call<List<Restaurant>> getAllRestaurants();
}
