package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by i.tesanovic on 7.9.2017..
 */

public class TableReservation {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("ReservationId")
    @Expose
    private Integer reservationId;

    @SerializedName("TableId")
    @Expose
    private Integer tableId;

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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}
