package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pass implements Parcelable{

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("route")
@Expose
private Route route;
@SerializedName("amount")
@Expose
private Integer amount;
@SerializedName("discount")
@Expose
private Integer discount;
@SerializedName("validity")
@Expose
private String validity;
@SerializedName("rides")
@Expose
private Integer rides;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("id")
@Expose
private String id;

    protected Pass(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readInt();
        }
        if (in.readByte() == 0) {
            discount = null;
        } else {
            discount = in.readInt();
        }
        validity = in.readString();
        if (in.readByte() == 0) {
            rides = null;
        } else {
            rides = in.readInt();
        }
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
        id = in.readString();
    }

    public static final Creator<Pass> CREATOR = new Creator<Pass>() {
        @Override
        public Pass createFromParcel(Parcel in) {
            return new Pass(in);
        }

        @Override
        public Pass[] newArray(int size) {
            return new Pass[size];
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

public Route getRoute() {
return route;
}

public void setRoute(Route route) {
this.route = route;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

public Integer getDiscount() {
return discount;
}

public void setDiscount(Integer discount) {
this.discount = discount;
}

public String getValidity() {
return validity;
}

public void setValidity(String validity) {
this.validity = validity;
}

public Integer getRides() {
return rides;
}

public void setRides(Integer rides) {
this.rides = rides;
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
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(amount);
        }
        if (discount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(discount);
        }
        dest.writeString(validity);
        if (rides == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rides);
        }
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
        dest.writeString(id);
    }
}