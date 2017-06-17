package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ivana on 2.5.2017..
 */

public class Reservation {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("User")
    @Expose
    private Integer user;
    @SerializedName("RestaurantTable")
    @Expose
    private Integer restaurantTable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(Integer restaurantTable) {
        this.restaurantTable = restaurantTable;
    }
}
