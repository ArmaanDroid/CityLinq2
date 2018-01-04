package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Completed implements Parcelable{

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
private Driver driver;
@SerializedName("vehicle")
@Expose
private String vehicle;
@SerializedName("vehicleNumber")
@Expose
private String vehicleNumber;
@SerializedName("transportName")
@Expose
private String transportName;
@SerializedName("time")
@Expose
private String time;
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
@SerializedName("timings")
@Expose
private String timings;
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


    protected Completed(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        user = in.readString();
        vehicle = in.readString();
        vehicleNumber = in.readString();
        transportName = in.readString();
        time = in.readString();
        payment = in.readString();
        qrCode = in.readString();
        date = in.readString();
        timings = in.readString();
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
    }

    public static final Creator<Completed> CREATOR = new Creator<Completed>() {
        @Override
        public Completed createFromParcel(Parcel in) {
            return new Completed(in);
        }

        @Override
        public Completed[] newArray(int size) {
            return new Completed[size];
        }
    };

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

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

public Driver getDriver() {
return driver;
}

public void setDriver(Driver driver) {
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
return time;
}

public void setTime(String time) {
this.time = time;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(user);
        dest.writeString(vehicle);
        dest.writeString(vehicleNumber);
        dest.writeString(transportName);
        dest.writeString(time);
        dest.writeString(payment);
        dest.writeString(qrCode);
        dest.writeString(date);
        dest.writeString(timings);
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
    }

}