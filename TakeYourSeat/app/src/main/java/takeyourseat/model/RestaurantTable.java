package takeyourseat.model;

/**
 * Created by Ivana on 2.5.2017..
 */

public class RestaurantTable extends BaseBean {

    public int number;
    public int restaurant;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }
}
