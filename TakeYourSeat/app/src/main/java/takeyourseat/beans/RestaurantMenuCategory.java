package takeyourseat.beans;

/**
 * Created by Ivana on 2.5.2017..
 */

public class RestaurantMenuCategory extends BaseBean {

    public String name;
    public int restaurant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }
}
