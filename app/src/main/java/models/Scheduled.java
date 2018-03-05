package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scheduled implements Parcelable{

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("user")
@Expose
private String user;
@SerializedName("driver")
@Expose
private String driver;
@SerializedName("vehicle")
@Expose
private String vehicle;
@SerializedName("vehicleNumber")
@Expose
private String vehicleNumber;
@SerializedName("transportName")
@Expose
private String transportName;
@SerializedName("timings")
@Expose
private String timings;
@SerializedName("payment")
@Expose
private String payment;
@SerializedName("qrCode")
@Expose
private String qrCode;
@SerializedName("source")
@Expose
private Source source;
@SerializedName("destination")
@Expose
private Destination destination;
@SerializedName("date")
@Expose
private String date;
@SerializedName("paymentDate")
@Expose
private String paymentDate;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("ticket")
@Expose
private Integer ticket;
@SerializedName("id")
@Expose
private String id;
@SerializedName("route")
@Expose
private Route route;
@SerializedName("vehicle_start_time")
@Expose
private String vehicle_start_time;

@SerializedName("tripId")
@Expose
private String tripId;


    protected Scheduled(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        user = in.readString();
        driver = in.readString();
        vehicle = in.readString();
        vehicleNumber = in.readString();
        transportName = in.readString();
        timings = in.readString();
        payment = in.readString();
        qrCode = in.readString();
        date = in.readString();
        paymentDate = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
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
        vehicle_start_time = in.readString();
        tripId = in.readString();
    }

    public static final Creator<Scheduled> CREATOR = new Creator<Scheduled>() {
        @Override
        public Scheduled createFromParcel(Parcel in) {
            return new Scheduled(in);
        }

        @Override
        public Scheduled[] newArray(int size) {
            return new Scheduled[size];
        }
    };

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

    public String getVehicle_start_time() {
        return vehicle_start_time;
    }

    public void setVehicle_start_time(String vehicle_start_time) {
        this.vehicle_start_time = vehicle_start_time;
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

public String getDriver() {
return driver;
}

public void setDriver(String driver) {
this.driver = driver;
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

public String getTime() {
return timings;
}

public void setTime(String time) {
this.timings = time;
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

public Destination getDestination() {
return destination;
}

public void setDestination(Destination destination) {
this.destination = destination;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
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

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }


    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(user);
        dest.writeString(driver);
        dest.writeString(vehicle);
        dest.writeString(vehicleNumber);
        dest.writeString(transportName);
        dest.writeString(timings);
        dest.writeString(payment);
        dest.writeString(qrCode);
        dest.writeString(date);
        dest.writeString(paymentDate);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
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
        dest.writeString(vehicle_start_time);
        dest.writeString(tripId);
    }
}