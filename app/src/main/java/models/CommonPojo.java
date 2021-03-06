package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armaan on 02-11-2017.
 */

public class CommonPojo {
    private boolean success;
    private String message;
    private String requestId;
    private String transactionId;
    private String token;
    private Float wallet;
    private Integer reviewPending;
    private User user;
    private List<Station> station = null;

    private PendingReviewtrip pendingReviewtrip;
    private List<Station> destinationStations = null;
    private List<Scheduled> schedule = null;
    private List<Object> favTrips = null;
    private List<TransportList> transportList = null;
    private List<RouteList> routeList = null;
    private List<Scheduled> scheduled = null;
    private List<Completed> completed = null;
    private List<WalletDetail> walletDetails = null;
    private List<Pass> passes = null;
    private List<PurchasePass> purchasePasses = null;
    private Ticket ticket;
    private PurchasePass pass;

    public PendingReviewtrip getPendingReviewtrip() {
        return pendingReviewtrip;
    }

    public void setPendingReviewtrip(PendingReviewtrip pendingReviewtrip) {
        this.pendingReviewtrip = pendingReviewtrip;
    }

    public Integer getReviewPending() {
        return reviewPending;
    }

    public void setReviewPending(Integer reviewPending) {
        this.reviewPending = reviewPending;
    }

    private List<CityList> cityList = null;

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
    }

    public List<Station> getDestinationStations() {
        return destinationStations;
    }

    public void setDestinationStations(List<Station> destinationStations) {
        this.destinationStations = destinationStations;
    }

    public List<WalletDetail> getWalletDetails() {
        return walletDetails;
    }

    public void setWalletDetails(List<WalletDetail> walletDetails) {
        this.walletDetails = walletDetails;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RouteList> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<RouteList> routeList) {
        this.routeList = routeList;
    }

    public PurchasePass getPass() {
        return pass;
    }

    public void setPass(PurchasePass pass) {
        this.pass = pass;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<PurchasePass> getPurchasePasses() {
        return purchasePasses;
    }

    public void setPurchasePasses(List<PurchasePass> purchasePasses) {
        this.purchasePasses = purchasePasses;
    }

    public List<Pass> getPasses() {
        return passes;
    }

    public void setPasses(List<Pass> passes) {
        this.passes = passes;
    }

    public List<Scheduled> getScheduled() {
        return scheduled;
    }

    public void setScheduled(List<Scheduled> scheduled) {
        this.scheduled = scheduled;
    }

    public List<Completed> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Completed> completed) {
        this.completed = completed;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Station> getStations() {
        return station;
    }

    public void setStations(List<Station> stations) {
        this.station = stations;
    }

    public List<TransportList> getTransportList() {
        return transportList;
    }

    public void setTransportList(List<TransportList> transportList) {
        this.transportList = transportList;
    }

    public List<Scheduled> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Scheduled> schedule) {
        this.schedule = schedule;
    }

    public List<Object> getFavTrips() {
        return favTrips;
    }

    public void setFavTrips(List<Object> favTrips) {
        this.favTrips = favTrips;
    }

    public Float getWallet() {
        return wallet;
    }

    public void setWallet(Float wallet) {
        this.wallet = wallet;
    }
}
