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

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import java.util.Calendar;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dialog.PayByWalletDialog;
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
    @BindView(R.id.textViewNetBanking)
    TextView textViewNetBanking;

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
        textViewNetBanking.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
        amountPayableWallet.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
    }

    @OnClick(R.id.textViewCard)
    void payUsingCard() {
        FragTransactFucntion.addFragFromRightFadeHistory(getFragmentManager(), SelectCardFragment.newInstance(fare, ""), R.id.frame_container_main);
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
        webRequestData.setTime(transportList.getTimings());
        webRequestData.setPayment(fare);
        webRequestData.setTicket(ticketCount);
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
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragTransactFucntion.replaceFragFromFadeWithoutHistory(getFragmentManager()
                        , RecieptFragment.newInstance(commonPojo.getTicket(), stationDest.getName(), stationSource.getName(), true, false), R.id.frame_container_main);
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    @OnClick(R.id.textViewNetBanking)
    public void onBraintreeSubmit(View v) {

//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI2MGQ2MTQ5YjgzZGUzNmNmMzhiNjBlYmViYjk2YmE2YjZjZDlhYzdlNTU0NTI4YThkMDA3ZjYwNDU5NjZiNjQyfGNyZWF0ZWRfYXQ9MjAxNy0xMi0xOVQxMDozNTo0OC4xMjY4MjAzNjArMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
//
//        startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
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
}
