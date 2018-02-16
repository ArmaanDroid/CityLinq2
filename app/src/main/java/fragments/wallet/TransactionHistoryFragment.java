package fragments.wallet;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.TransactionHistoryAdapter;
import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import models.CommonPojo;
import models.WalletDetail;
import models.WalletDetailPerMonth;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionHistoryFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;


    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.no_record_text2)
    ImageView no_record_text2;
    private long timeOnFirstDayOfMonth;
    private long timeOnLastDayOfMonth;

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionHistoryFragment newInstance(String param1, String param2) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_with_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_TRANSACTION_HISTORY_FRAGMENT, "");
    }

    private void initView() {
        if (isNetworkConnected())
            getDataFromServer();
        else
            showNoInternetConnection(no_record_text2);
    }

    private void getDataFromServer() {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.WALLET_DETAILS + AppConstants.USER_ID);
        makeGetRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                if (commonPojo.getWalletDetails() == null) {
                    showNoDataFound(no_record_text2);
                    return;
                }
                if (commonPojo.getWalletDetails().size() < 1) {
                    showNoDataFound(no_record_text2);
                    return;
                }
                recyclerView.setAdapter(new TransactionHistoryAdapter(getMOnthAccordingList(commonPojo.getWalletDetails())));
            }

            @Override
            public void failure() throws Exception {
                showServerDown(no_record_text2);
            }
        });
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy");

    private List<WalletDetailPerMonth> getMOnthAccordingList(List<WalletDetail> walletDetails) {
        List<WalletDetailPerMonth> walletDetailPerMonthList = new ArrayList<>();
        WalletDetailPerMonth walletDetailPerMonth = new WalletDetailPerMonth();

        timeOnFirstDayOfMonth = getFirstDayOfMonthMillSec(walletDetails.get(0).getDate());
        timeOnLastDayOfMonth = getLastDayOfMonthMillSec(walletDetails.get(0).getDate());

        walletDetailPerMonth.setMonthYear(simpleDateFormat.format(timeOnFirstDayOfMonth));

        for (WalletDetail detail : walletDetails) {
            if (detail.getDate() > timeOnFirstDayOfMonth & detail.getDate() < timeOnLastDayOfMonth) {
                walletDetailPerMonth.transactions.add(detail);

            } else {
                walletDetailPerMonthList.add(walletDetailPerMonth);

                walletDetailPerMonth = new WalletDetailPerMonth();
                timeOnFirstDayOfMonth = getFirstDayOfMonthMillSec(detail.getDate());
                timeOnLastDayOfMonth = getLastDayOfMonthMillSec(detail.getDate());
                walletDetailPerMonth.setMonthYear(simpleDateFormat.format(timeOnFirstDayOfMonth));
                if (detail.getDate() > timeOnFirstDayOfMonth & detail.getDate() < timeOnLastDayOfMonth) {
                    walletDetailPerMonth.transactions.add(detail);
                    //check if it is last entry in the list
                    if (walletDetails.indexOf(detail) == walletDetails.size() - 1)
                        continue;

                }
            }

            if (walletDetails.indexOf(detail) == walletDetails.size() - 1) {
                walletDetailPerMonthList.add(walletDetailPerMonth);
            }
        }

        return walletDetailPerMonthList;
    }

    private long getLastDayOfMonthMillSec(Long date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 60);
        calendar.set(Calendar.SECOND, 60);
        calendar.set(Calendar.MILLISECOND, 1000);

        return calendar.getTimeInMillis();
    }

    private long getFirstDayOfMonthMillSec(Long date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener.changeUIAccToFragment(AppConstants.TAG_WALLET_FRAGMENT, "");
        unbinder.unbind();
    }


}
