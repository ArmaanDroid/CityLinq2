package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteList implements Parcelable {

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("name")
@Expose
private String name;
@SerializedName("timing")
@Expose
private String timing;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("stations")
@Expose
private List<Station> stations = null;
@SerializedName("id")
@Expose
private String id;

    protected RouteList(Parcel in) {
        updatedAt = in.readString();
        createdAt = in.readString();
        name = in.readString();
        timing = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        stations = in.createTypedArrayList(Station.CREATOR);
        id = in.readString();
    }

    public static final Creator<RouteList> CREATOR = new Creator<RouteList>() {
        @Override
        public RouteList createFromParcel(Parcel in) {
            return new RouteList(in);
        }

        @Override
        public RouteList[] newArray(int size) {
            return new RouteList[size];
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

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getTiming() {
return timing;
}

public void setTiming(String timing) {
this.timing = timing;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public List<Station> getStations() {
return stations;
}

public void setStations(List<Station> stations) {
this.stations = stations;
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
        dest.writeString(name);
        dest.writeString(timing);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        dest.writeTypedList(stations);
        dest.writeString(id);
    }
}