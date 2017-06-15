package takeyourseat.model;

/**
 * Created by Ivana on 2.5.2017..
 */

public class Comment extends BaseBean {

    public String description;
    public int user;
    public int restaurant;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }
}
