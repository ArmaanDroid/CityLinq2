package fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import models.Station;
import models.TransportList;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookMyTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookMyTripFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private Station stationSource;
    private Station stationDestination;
    private TransportList transportList;

    private Unbinder unbinder;

    @BindView(R.id.textViewDateBookTicket)
    TextView textViewDateBookTicket;

    @BindView(R.id.textViewSource)
    TextView textViewSource;

    @BindView(R.id.textViewDestination)
    TextView textViewDestination;

    @BindView(R.id.textView25)
    TextView textViewVehicleNumber;

    @BindView(R.id.textView22)
    TextView textViewTransportName;

    @BindView(R.id.textViewPassengerCount)
    TextView textViewPassengerCount;

    @BindView(R.id.textView261)
    TextView textViewFare;

    private int passengerCount;

    public BookMyTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookMyTripFragment newInstance(Station param1, Station param2, TransportList param3) {
        BookMyTripFragment fragment = new BookMyTripFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        args.putParcelable(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stationSource = getArguments().getParcelable(ARG_PARAM1);
            stationDestination = getArguments().getParcelable(ARG_PARAM2);
            transportList = getArguments().getParcelable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_my_trip, container, false);
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
        mListener.changeUIAccToFragment(AppConstants.TAG_BOOK_MY_TRIP_FRAGMENT, "");
        Log.d("TAG", "onResume: book my trip");
    }

    private void initView() {
        textViewDateBookTicket.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_calendar_white), null, null, null);

        textViewSource.setText(stationSource.getName());
        textViewDestination.setText(stationDestination.getName());
        textViewTransportName.setText(transportList.getTransportName());
        textViewVehicleNumber.setText(transportList.getMakeAndModel());
        textViewFare.setText(setPriceAsText(String.valueOf(Float.valueOf(transportList.getFare()))));
    }

    @OnClick({R.id.imageMinus, R.id.imageAdd})
    void incrementDecrementPassenger(View view) {
        passengerCount = Integer.parseInt(textViewPassengerCount.getText().toString());

        if (view.getId() == R.id.imageAdd) {
            passengerCount++;
            textViewPassengerCount.setText(String.valueOf(passengerCount));
            textViewFare.setText(setPriceAsText(String.valueOf(Float.valueOf(transportList.getFare()) * passengerCount)));
        }
        if (view.getId() == R.id.imageMinus) {
            if (passengerCount < 2)
                return;
            passengerCount--;
            textViewFare.setText(setPriceAsText(String.valueOf(Float.valueOf(transportList.getFare()) * passengerCount)));
            textViewPassengerCount.setText(String.valueOf(passengerCount));
        }
    }

    @OnClick(R.id.continueButton)
    void continueToPayment() {
        String fare=textViewFare.getText().toString().replace("$","");
        FragTransactFucntion.addFragFromFadeHistory(getFragmentManager(),  ChoosePamentFragment.newInstance(fare,stationSource,stationDestination,transportList), R.id.frame_container_main);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener.changeUIAccToFragment(AppConstants.TAG_BROWSE_LINQS_FRAGMENT, "");
    }

}
