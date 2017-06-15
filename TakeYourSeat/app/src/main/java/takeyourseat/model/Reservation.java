package takeyourseat.beans;

/**
 * Created by Ivana on 2.5.2017..
 */

public class Reservation extends BaseBean {

    public int user;
    public int restaurantTable;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(int restaurantTable) {
        this.restaurantTable = restaurantTable;
    }
}
