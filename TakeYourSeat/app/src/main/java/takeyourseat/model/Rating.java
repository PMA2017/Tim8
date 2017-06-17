package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ivana on 2.5.2017..
 */

public class Rating  {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Rank")
    @Expose
    private Integer rank;
    @SerializedName("Restaurant")
    @Expose
    private Integer restaurant;
    @SerializedName("User")
    @Expose
    private Integer user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
