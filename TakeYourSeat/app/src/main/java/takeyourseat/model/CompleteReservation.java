package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nenad on 9/10/2017.
 */

public class CompleteReservation {

    @SerializedName("Reservation")
    @Expose
    private String reservation;

    @SerializedName("TableIds")
    @Expose
    private List<String> tableIds;

    @SerializedName("FriendIds")
    @Expose
    private List<String> friendIds;


    public List<String> getTableIds() {
        return tableIds;
    }

    public void setTableIds(List<String> tableIds) {
        this.tableIds = tableIds;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }
}
