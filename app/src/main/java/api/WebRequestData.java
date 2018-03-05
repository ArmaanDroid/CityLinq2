package api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Armaan on 03-11-2017.
 */

public class WebRequestData {

    private  String requestEndPoint;

    private String name;
    private String timeOffset;
    private String email;
    private String password;
    private String mobileNumber;
    private String deviceId;
    private String facebookId;
    private String to;
    private String requestId;
    private String code;
    private String city;
    private String userId;
    private String type;
    private String tripId;
    private String driverId;
    private String rating;
    private String review;
    private String latitude;
    private String longitude;
    private String amount;
    private String vehicleId;
    private String vehicleNumber;
    private String transportName;
    private String time;
    private String payment;
    private String ticket ;
    private String source ;
    private String destination ;
    private String date ;
    private String expiaryDate ;
    private String passId ;
    private String routeId ;
    private String bookId ;
    private String starttime ;
    private String promocode ;
    private String nounce ;
    private String station ;
    private String rides ;
    private String wallet ;
    private Integer direction ;
    private String vehicle_start_time  ;


    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setVehicle_start_time(String vehicle_start_time) {
        this.vehicle_start_time = vehicle_start_time;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public void setTimeOffset(String timeOffset) {
        this.timeOffset = timeOffset;
    }

    public void setRides(String rides) {
        this.rides = rides;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setNounce(String nounce) {
        this.nounce = nounce;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setExpiaryDate(String expiaryDate) {
        this.expiaryDate = expiaryDate;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

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
