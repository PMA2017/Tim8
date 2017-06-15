package takeyourseat.model;

/**
 * Created by Ivana on 2.5.2017..
 */

public class Rating extends BaseBean {

    public int rank;
    public int restaurant;
    public int user;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
