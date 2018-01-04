package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("hash")
@Expose
private String hash;
@SerializedName("salt")
@Expose
private String salt;
@SerializedName("fleetId")
@Expose
private String fleetId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
@SerializedName("mobileNumber")
@Expose
private String mobileNumber;
@SerializedName("licence")
@Expose
private String licence;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("phoneVerified")
@Expose
private Integer phoneVerified;
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

public String getHash() {
return hash;
}

public void setHash(String hash) {
this.hash = hash;
}

public String getSalt() {
return salt;
}

public void setSalt(String salt) {
this.salt = salt;
}

public String getFleetId() {
return fleetId;
}

public void setFleetId(String fleetId) {
this.fleetId = fleetId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getMobileNumber() {
return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
this.mobileNumber = mobileNumber;
}

public String getLicence() {
return licence;
}

public void setLicence(String licence) {
this.licence = licence;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public Integer getPhoneVerified() {
return phoneVerified;
}

public void setPhoneVerified(Integer phoneVerified) {
this.phoneVerified = phoneVerified;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}