package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class TransportList implements Parcelable {

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("fleetId")
    @Expose
    private String fleetId;
    @SerializedName("vehicleNumber")
    @Expose
    private String vehicleNumber;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("transportName")
    @Expose
    private String transportName;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("route")
    @Expose
    private Route route;
    @SerializedName("timings")
    @Expose
    private String[] timings;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("makeAndModel")
    @Expose
    private String makeAndModel;
    @SerializedName("approxTimeDuration")
    @Expose
    private String approxTimeDuration;
    @SerializedName("vehicleColor")
    @Expose
    private String vehicleColor;
    @SerializedName("features")
    @Expose
    private List<String> features = null;
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("returntiming")
    @Expose
    private List<String> returntiming = null;
    @SerializedName("stationsTimings")
    @Expose
    private List<String> stationsTimings = null;

    @SerializedName("direction")
    @Expose
    private Integer direction;

    private String fare;
    private Integer availableSeats;
    private String vehicle_start_time;

    @SerializedName("startDay")
    @Expose
    private long startDay;

    protected TransportList(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        fleetId = in.readString();
        vehicleNumber = in.readString();
        transportName = in.readString();
        if (in.readByte() == 0) {
            seats = null;
        } else {
            seats = in.readInt();
        }
        timings = in.createStringArray();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        makeAndModel = in.readString();
        approxTimeDuration = in.readString();
        vehicleColor = in.readString();
        features = in.createStringArrayList();
        id = in.readString();
        returntiming = in.createStringArrayList();
        stationsTimings = in.createStringArrayList();
        if (in.readByte() == 0) {
            direction = null;
        } else {
            direction = in.readInt();
        }
        fare = in.readString();
        if (in.readByte() == 0) {
            availableSeats = null;
        } else {
            availableSeats = in.readInt();
        }
        vehicle_start_time = in.readString();
       startDay=in.readLong();
    }

    public static final Creator<TransportList> CREATOR = new Creator<TransportList>() {
        @Override
        public TransportList createFromParcel(Parcel in) {
            return new TransportList(in);
        }

        @Override
        public TransportList[] newArray(int size) {
            return new TransportList[size];
        }
    };

    public String getVehicle_start_time() {
        return vehicle_start_time;
    }

    public void setVehicle_start_time(String vehicle_start_time) {
        this.vehicle_start_time = vehicle_start_time;
    }


    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
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

    public String getFleetId() {
        return fleetId;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String[] getTimings() {
        return timings;
    }

    public void setTimings(String[] timings) {
        this.timings = timings;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public void setMakeAndModel(String makeAndModel) {
        this.makeAndModel = makeAndModel;
    }

    public String getApproxTimeDuration() {
        return approxTimeDuration;
    }

    public void setApproxTimeDuration(String approxTimeDuration) {
        this.approxTimeDuration = approxTimeDuration;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(fleetId);
        dest.writeString(vehicleNumber);
        dest.writeString(transportName);
        if (seats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(seats);
        }
        dest.writeStringArray(timings);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        dest.writeString(makeAndModel);
        dest.writeString(approxTimeDuration);
        dest.writeString(vehicleColor);
        dest.writeStringList(features);
        dest.writeString(id);
        dest.writeStringList(returntiming);
        dest.writeStringList(stationsTimings);
        if (direction == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(direction);
        }
        dest.writeString(fare);
        if (availableSeats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableSeats);
        }
        dest.writeString(vehicle_start_time);
        dest.writeLong(startDay);
    }


}