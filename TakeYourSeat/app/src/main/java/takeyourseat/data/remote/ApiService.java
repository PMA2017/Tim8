package takeyourseat.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import takeyourseat.model.Comment;
import takeyourseat.model.Location;
import takeyourseat.model.MenuItem;
import takeyourseat.model.Rating;
import takeyourseat.model.Reservation;
import takeyourseat.model.ReservationTable;
import takeyourseat.model.Restaurant;
import takeyourseat.model.RestaurantTable;
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

    @PUT("api/data/insert/comment")
    Call<String> insertComment(@Body Comment comment);

    @PUT("api/data/insert/rating")
    Call<String> insertRating(@Body Rating rating);

    @PUT("api/data/update/user/{columnName}/{value}")
    Call<Boolean> updateUser(@Path("columnName") String columnName, @Path("value") String value, @Body User user);

    @GET("api/data/getreservations/{restaurantId}")
    Call<List<ReservationTable>> getReservationTables(@Path("restaurantId") int restaurantId);

    @GET("api/data/getby/menuitem/restaurant/{value}")
    Call<List<MenuItem>> getMenuItemsForRestaurant(@Path("value") String restaurantId);

    @GET("api/data/getby/comment/restaurant/{value}")
    Call<List<Comment>> getCommentsForRestaurant(@Path("value") String restaurantId);

    @GET("api/data/getby/rating/restaurant/{value}")
    Call<List<Rating>> getRatingForRestaurant(@Path("value") String restaurantId);

    @GET("api/data/getby/location/id/{value}")
    Call<List<Location>> getLocationById(@Path("value") String id);

    @GET("/api/data/getby/restauranttable/restaurant/{value}")
    Call<List<RestaurantTable>> getRestaurantTables(@Path("value") String restaurantId);

    @PUT("api/data/insert/reservation")
    Call<String> insertReservation(@Body Reservation reservation);
}
