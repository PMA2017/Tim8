package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Nenad on 6/16/2017.
 */

public class ReservationTable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("StartDate")
    @Expose
    private String startDate;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
