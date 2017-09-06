package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ivana on 7.9.2017..
 */

public class ReservationFriends {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ReservationId")
    @Expose
    private Integer reservationId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Status")
    @Expose
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
