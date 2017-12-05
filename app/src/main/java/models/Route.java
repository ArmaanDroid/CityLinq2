package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

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

}