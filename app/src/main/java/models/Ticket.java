package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket implements Parcelable {

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
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("ticket")
    @Expose
    private Integer ticket;

    @SerializedName("vehicle_start_time")
    @Expose
    private String vehicle_start_time;

    protected Ticket(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        user = in.readString();
        vehicle = in.readString();
        vehicleNumber = in.readString();
        transportName = in.readString();
        timings = in.readString();
        payment = in.readString();
        qrCode = in.readString();
        source = in.readString();
        destination = in.readString();
        date = in.readString();
        paymentDate = in.readString();
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
        vehicle_start_time = in.readString();
        if (in.readByte() == 0) {
            adapterPosition = null;
        } else {
            adapterPosition = in.readInt();
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
        dest.writeString(timings);
        dest.writeString(payment);
        dest.writeString(qrCode);
        dest.writeString(source);
        dest.writeString(destination);
        dest.writeString(date);
        dest.writeString(paymentDate);
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
        dest.writeString(vehicle_start_time);
        if (adapterPosition == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(adapterPosition);
        }
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public String getVehicle_start_time() {
        return vehicle_start_time;
    }

    public void setVehicle_start_time(String vehicle_start_time) {
        this.vehicle_start_time = vehicle_start_time;
    }

    private Integer adapterPosition;


    @SerializedName("id")
    @Expose
    private String id;

    public Ticket() {
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

    public String getTimings() {
        return vehicle_start_time;
    }

    public void setTimings(String timings) {
        this.vehicle_start_time = timings;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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

    public Integer getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(Integer adapterPosition) {
        this.adapterPosition = adapterPosition;
    }
}