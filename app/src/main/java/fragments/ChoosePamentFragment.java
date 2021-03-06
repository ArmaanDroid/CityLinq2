package fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.GooglePayment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.GooglePaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;

import java.util.Calendar;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dialog.PayByWalletDialog;
import fragments.passes.SelectPassFragment;
import listners.AdapterItemClickListner;
import models.CommonPojo;
import models.Station;
import models.TransportList;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoosePamentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoosePamentFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final int REQUEST_CODE = 3214;
    private static final int SELECT_PASS_REQUEST_CODE = 2564;
    private static String ARG_PARAM4 = "param4";
    private static String ARG_PARAMTicket = "ARG_PARAMTicket";

    // TODO: Rename and change types of parameters
    private String fare;
    private Station stationSource;
    private Station stationDest;
    private TransportList transportList;
    private String ticketCount;

    private Unbinder unbinder;

    @BindView(R.id.textViewAddPromoCode)
    TextView textViewAddPromoCode;

    @BindView(R.id.amountPayableWallet)
    TextView amountPayableWallet;

    @BindView(R.id.textViewAmountPayable)
    TextView textViewAmountPayable;

    @BindView(R.id.textViewCard)
    TextView textViewCard;

    private BraintreeFragment mBraintreeFragment;
    private PaymentMethodNonceCreatedListener paymentMethodNonce;
    private boolean isByWallet;

    public ChoosePamentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * \
     *
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoosePamentFragment newInstance(String param1, String ticketCount, Station stationSource, Station stationDest, TransportList transportList) {
        ChoosePamentFragment fragment = new ChoosePamentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAMTicket, ticketCount);
        args.putParcelable(ARG_PARAM2, stationSource);
        args.putParcelable(ARG_PARAM3, stationDest);
        args.putParcelable(ARG_PARAM4, transportList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fare = getArguments().getString(ARG_PARAM1);
            ticketCount = getArguments().getString(ARG_PARAMTicket);
            stationSource = getArguments().getParcelable(ARG_PARAM2);
            stationDest = getArguments().getParcelable(ARG_PARAM3);
            transportList = getArguments().getParcelable(ARG_PARAM4);
        }
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(getActivity(), AppConstants.BRAINTREE_TOKEN);
//            mBraintreeFragment.addListener(paymentMethodNonce);
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        GooglePayment.isReadyToPay(mBraintreeFragment, new BraintreeResponseListener<Boolean>() {
            @Override
            public void onResponse(Boolean isReadyToPay) {
                if (isReadyToPay) {
                    // Show Google Pay button
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_CHOOSE_PAYMENT_FRAGMENT, "");
    }

    private void initView() {

        amountPayableWallet.setText(setPriceAsText(String.valueOf(AppConstants.WALLET_BALANCE)));
        textViewAmountPayable.setText(setPriceAsText(fare));

        textViewAddPromoCode.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
        textViewCard.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
//        textViewNetBanking.setC   ompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
        amountPayableWallet.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
    }

    @OnClick(R.id.textViewAddPromoCode)
    void usePasses() {
        SelectPassFragment fragment = new SelectPassFragment();
        fragment.setTargetFragment(this, SELECT_PASS_REQUEST_CODE);

        FragTransactFucntion.addFragFromRightFadeHistory(getFragmentManager(), fragment, R.id.frame_container_main);
    }


    @OnClick(R.id.textViewCard)
    public void onBraintreeSubmit(View v) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(AppConstants.BRAINTREE_TOKEN);
        GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
                .transactionInfo(TransactionInfo.newBuilder()
                        .setTotalPrice(fare)
                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                        .setCurrencyCode("USD")
                        .build());
        dropInRequest.googlePaymentRequest(googlePaymentRequest);

        dropInRequest.amount(fare);
        startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                sendNonceToServer(result);
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                error.printStackTrace();
            }
        } else if (requestCode == SELECT_PASS_REQUEST_CODE) {

            WebRequestData webRequestData = new WebRequestData();
            webRequestData.setRequestEndPoint(RequestEndPoints.USE_PASS);
            webRequestData.setPassId(data.getStringExtra("pass_id"));
            webRequestData.setRides(ticketCount);
            updateData(webRequestData, new WeResponseCallback() {
                @Override
                public void onResponse(CommonPojo commonPojo) throws Exception {
                    bookTicket();
                }

                @Override
                public void failure() throws Exception {

                }
            });

        }
    }

    private void sendNonceToServer(DropInResult result) {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.BRAIN_TREE_PAYMENT);
        webRequestData.setNounce(result.getPaymentMethodNonce().getNonce());
//        webRequestData.setNounce("fake-valid-nonce");
        webRequestData.setAmount(fare);
        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                if (commonPojo.getTransactionId() != null) {
                    isByWallet=false;
                    bookTicket();
                }
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    @OnClick(R.id.amountPayableWallet)
    void payUsingWallet() {

        PayByWalletDialog payByWalletDialog = new PayByWalletDialog(fare, new AdapterItemClickListner() {
            @Override
            public void onClick(int position, String tag) {
                if (tag.equalsIgnoreCase("pay"))
                    initiatePamentByWallet();
            }
        });
        payByWalletDialog.show(getFragmentManager(), "payByWallet");
    }

    private void initiatePamentByWallet() {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setAmount(fare);
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setRequestEndPoint(RequestEndPoints.PAY_BY_WALLET);
        updateData(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                isByWallet=true;
                bookTicket();
            }

            @Override
            public void failure() throws Exception {
                showToast("Payment Failed");
            }
        });
    }

    private void bookTicket() {

        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setVehicleId(transportList.getId());
        webRequestData.setVehicleNumber(transportList.getVehicleNumber());
        webRequestData.setTransportName(transportList.getTransportName());
        webRequestData.setTime(transportList.getTimings()[0]);
        webRequestData.setPayment(fare);
        webRequestData.setVehicle_start_time(transportList.getVehicle_start_time());
        webRequestData.setTicket(ticketCount);

        webRequestData.setWallet(String.valueOf(AppConstants.WALLET_BALANCE - Double.valueOf(fare)));
        webRequestData.setDirection(transportList.getDirection());
        webRequestData.setSource(stationSource.getId());
        webRequestData.setRouteId(transportList.getRoute().getId());
        webRequestData.setDestination(stationDest.getId());

        try {
            calendar.setTime(AppConstants.JOURNEY_DATE);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Log.d("TAG", "bookTicket: " + calendar.getTimeInMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

        webRequestData.setDate(String.valueOf(calendar.getTimeInMillis()));

        webRequestData.setRequestEndPoint(RequestEndPoints.BOOK_A_RIDE);

        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                if(isByWallet)
                AppConstants.WALLET_BALANCE=AppConstants.WALLET_BALANCE - Float.valueOf(fare);

                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragTransactFucntion.replaceFragFromFadeWithoutHistory(getFragmentManager()
                        , RecieptFragment.newInstance(commonPojo.getTicket(), stationDest.getName(), stationSource.getName(), true, false), R.id.frame_container_main);
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener.changeUIAccToFragment(AppConstants.TAG_BOOK_MY_TRIP_FRAGMENT, "");
    }

//    @Override
//    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
//
//    }
}
