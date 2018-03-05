package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingReviewtrip implements Parcelable {

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("user")
@Expose
private String user;
@SerializedName("vehicle")
@Expose
private String vehicle;
@SerializedName("vehicleNumber")
@Expose
private String vehicleNumber;
@SerializedName("transportName")
@Expose
private String transportName;
@SerializedName("vehicle_start_time")
@Expose
private String vehicleStartTime;
@SerializedName("payment")
@Expose
private String payment;
@SerializedName("qrCode")
@Expose
private String qrCode;
@SerializedName("source")
@Expose
private Source source;
@SerializedName("route")
@Expose
private Route route;
@SerializedName("destination")
@Expose
private Destination destination;
@SerializedName("date")
@Expose
private Long date;
@SerializedName("start_of_day")
@Expose
private Long startOfDay;
@SerializedName("paymentDate")
@Expose
private String paymentDate;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("tripId")
@Expose
private String tripId;
@SerializedName("driver")
@Expose
private Driver driver;
@SerializedName("direction")
@Expose
private Integer direction;
@SerializedName("isFav")
@Expose
private Integer isFav;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("ticket")
@Expose
private Integer ticket;
@SerializedName("id")
@Expose
private String id;


    protected PendingReviewtrip(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        user = in.readString();
        vehicle = in.readString();
        vehicleNumber = in.readString();
        transportName = in.readString();
        vehicleStartTime = in.readString();
        payment = in.readString();
        qrCode = in.readString();
        if (in.readByte() == 0) {
            date = null;
        } else {
            date = in.readLong();
        }
        if (in.readByte() == 0) {
            startOfDay = null;
        } else {
            startOfDay = in.readLong();
        }
        paymentDate = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        tripId = in.readString();
        if (in.readByte() == 0) {
            direction = null;
        } else {
            direction = in.readInt();
        }
        if (in.readByte() == 0) {
            isFav = null;
        } else {
            isFav = in.readInt();
        }
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            ticket = null;
        } else {
            ticket = in.readInt();
        }
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(user);
        dest.writeString(vehicle);
        dest.writeString(vehicleNumber);
        dest.writeString(transportName);
        dest.writeString(vehicleStartTime);
        dest.writeString(payment);
        dest.writeString(qrCode);
        if (date == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(date);
        }
        if (startOfDay == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(startOfDay);
        }
        dest.writeString(paymentDate);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        dest.writeString(tripId);
        if (direction == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(direction);
        }
        if (isFav == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isFav);
        }
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        if (ticket == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ticket);
        }
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PendingReviewtrip> CREATOR = new Creator<PendingReviewtrip>() {
        @Override
        public PendingReviewtrip createFromParcel(Parcel in) {
            return new PendingReviewtrip(in);
        }

        @Override
        public PendingReviewtrip[] newArray(int size) {
            return new PendingReviewtrip[size];
        }
    };

    public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getUser() {
return user;
}

public void setUser(String user) {
this.user = user;
}

public String getVehicle() {
return vehicle;
}

public void setVehicle(String vehicle) {
this.vehicle = vehicle;
}

public String getVehicleNumber() {
return vehicleNumber;
}

public void setVehicleNumber(String vehicleNumber) {
this.vehicleNumber = vehicleNumber;
}

public String getTransportName() {
return transportName;
}

public void setTransportName(String transportName) {
this.transportName = transportName;
}

public String getVehicleStartTime() {
return vehicleStartTime;
}

public void setVehicleStartTime(String vehicleStartTime) {
this.vehicleStartTime = vehicleStartTime;
}

public String getPayment() {
return payment;
}

public void setPayment(String payment) {
this.payment = payment;
}

public String getQrCode() {
return qrCode;
}

public void setQrCode(String qrCode) {
this.qrCode = qrCode;
}

public Source getSource() {
return source;
}

public void setSource(Source source) {
this.source = source;
}

public Route getRoute() {
return route;
}

public void setRoute(Route route) {
this.route = route;
}

public Destination getDestination() {
return destination;
}

public void setDestination(Destination destination) {
this.destination = destination;
}

public Long getDate() {
return date;
}

public void setDate(Long date) {
this.date = date;
}

public Long getStartOfDay() {
return startOfDay;
}

public void setStartOfDay(Long startOfDay) {
this.startOfDay = startOfDay;
}

public String getPaymentDate() {
return paymentDate;
}

public void setPaymentDate(String paymentDate) {
this.paymentDate = paymentDate;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public String getTripId() {
return tripId;
}

public void setTripId(String tripId) {
this.tripId = tripId;
}

public Driver getDriver() {
return driver;
}

public void setDriver(Driver driver) {
this.driver = driver;
}

public Integer getDirection() {
return direction;
}

public void setDirection(Integer direction) {
this.direction = direction;
}

public Integer getIsFav() {
return isFav;
}

public void setIsFav(Integer isFav) {
this.isFav = isFav;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Integer getTicket() {
return ticket;
}

public void setTicket(Integer ticket) {
this.ticket = ticket;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}