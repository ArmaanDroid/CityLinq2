package models;

/**
 * Created by Armaan on 03-11-2017.
 */

public class User {
    private String name;
    private String email;
    private String mobileNumber;
    private String id;
    private String deviceId;
    private String profilePic;
    private String reviewPending;
    private Integer ispromoUsed;
    private Integer phoneVerified;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getReviewPending() {
        return reviewPending;
    }

    public void setReviewPending(String reviewPending) {
        this.reviewPending = reviewPending;
    }

    public Integer getIspromoUsed() {
        return ispromoUsed;
    }

    public void setIspromoUsed(Integer ispromoUsed) {
        this.ispromoUsed = ispromoUsed;
    }

    public Integer getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Integer phoneVerified) {
        this.phoneVerified = phoneVerified;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
