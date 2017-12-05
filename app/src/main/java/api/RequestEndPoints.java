package api;

/**
 * Created by Armaan on 03-11-2017.
 */

public class RequestEndPoints {
    public final static String SIGNUP_URL="user/register";
    public final static String SEND_OTP="user/send";
    public final static String VERIFY_OTP="user/verify";
    public final static String CANCEL_OTP="user/cancelotp";
    public final static String LOGIN="user/login";
    public final static String GET_BOOK_DATA="user/bookdata/";

    public static final String GET_ROUTES = "user/transport";
    public static final String GET_FAVOURITE_TRIPS = "user/favtrips/";
    public static final String GET_MY_TRIPS = "user/mytrips/";
    public static final String GET_MY_PASSES = "user/passes/";
    public static final String ADD_TO_WALLET = "user/addwallet";
    public static final String PAY_BY_WALLET = "user/walletpayment";
    public static final String BOOK_A_RIDE = "user/booking";
}
