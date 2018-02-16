package models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;

public class CityList implements Searchable{

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("name")
@Expose
private String name;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("routeList")
@Expose
private List<RouteList> routeList = null;
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

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public List<RouteList> getRouteList() {
return routeList;
}

public void setRouteList(List<RouteList> routeList) {
this.routeList = routeList;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

    @Override
    public String getTitle() {
        return name;
    }
}