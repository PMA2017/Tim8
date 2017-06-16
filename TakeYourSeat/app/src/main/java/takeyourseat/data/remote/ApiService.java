package takeyourseat.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import takeyourseat.model.MenuItem;
import takeyourseat.model.ReservationTable;
import takeyourseat.model.Restaurant;
import takeyourseat.model.User;

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

    @GET("api/data/getby/restaurant/name/{value}")
    Call<List<Restaurant>> getRestaurantByName(@Path("value") String name);

    @PUT("api/data/insert/user")
    Call<String> insertUser(@Body User user);

    @PUT("api/data/update/user/{columnName}/{value}")
    Call<Boolean> updateUser(@Path("columnName") String columnName, @Path("value") String value, @Body User user);

    @GET("api/data/getreservations/{restaurantId}")
    Call<List<ReservationTable>> getReservationTables(@Path("restaurantId") int restaurantId);

    @GET("api/data/getby/menuitem/restaurant/{value}")
    Call<List<MenuItem>> getMenuItemsForRestaurant(@Path("value") String restaurantId);


}
