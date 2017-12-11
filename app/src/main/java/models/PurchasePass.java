package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchasePass implements Parcelable {

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("route")
@Expose
private String route;
@SerializedName("userId")
@Expose
private String userId;
@SerializedName("pass")
@Expose
private Pass pass;
@SerializedName("dateOfBuy")
@Expose
private long dateOfBuy;
@SerializedName("expiaryDate")
@Expose
private long expiaryDate;
@SerializedName("payment")
@Expose
private Integer payment;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("rideUsed")
@Expose
private Integer rideUsed;
@SerializedName("id")
@Expose
private String id;

    protected PurchasePass(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        route = in.readString();
        userId = in.readString();
        pass = in.readParcelable(Pass.class.getClassLoader());
        dateOfBuy = in.readLong();
        expiaryDate = in.readLong();

        if (in.readByte() == 0) {
            payment = null;
        } else {
            payment = in.readInt();
        }
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        if (in.readByte() == 0) {
            rideUsed = null;
        } else {
            rideUsed = in.readInt();
        }
        id = in.readString();
    }

    public static final Creator<PurchasePass> CREATOR = new Creator<PurchasePass>() {
        @Override
        public PurchasePass createFromParcel(Parcel in) {
            return new PurchasePass(in);
        }

        @Override
        public PurchasePass[] newArray(int size) {
            return new PurchasePass[size];
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

public String getRoute() {
return route;
}

public void setRoute(String route) {
this.route = route;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public Pass getPass() {
return pass;
}

public void setPass(Pass pass) {
this.pass = pass;
}

public long getDateOfBuy() {
return dateOfBuy;
}

public void setDateOfBuy(long dateOfBuy) {
this.dateOfBuy = dateOfBuy;
}

public long getExpiaryDate() {
return expiaryDate;
}

public void setExpiaryDate(long expiaryDate) {
this.expiaryDate = expiaryDate;
}

public Integer getPayment() {
return payment;
}

public void setPayment(Integer payment) {
this.payment = payment;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public Integer getRideUsed() {
return rideUsed;
}

public void setRideUsed(Integer rideUsed) {
this.rideUsed = rideUsed;
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
        dest.writeString(route);
        dest.writeString(userId);
        dest.writeParcelable(pass, flags);
        dest.writeLong(dateOfBuy);
        dest.writeLong(expiaryDate);

        if (payment == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(payment);
        }
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        if (rideUsed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rideUsed);
        }
        dest.writeString(id);
    }
}