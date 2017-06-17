package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ivana on 2.5.2017..
 */

public class RestaurantTable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("Restaurant")
    @Expose
    private Integer restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }
}
