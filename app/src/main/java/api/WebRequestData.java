package api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Armaan on 03-11-2017.
 */

public class WebRequestData {

    private  String requestEndPoint;

    private String name;
    private String email;
    private String password;
    private String mobileNumber;
    private String deviceId;
    private String facebookId;
    private String to;
    private String requestId;
    private String code;
    private String userId;
    private String type;


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRequestEndPoint() {
        return requestEndPoint;
    }

    public  void setRequestEndPoint(String requestEndPoint) {
        this.requestEndPoint = requestEndPoint;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }
}
