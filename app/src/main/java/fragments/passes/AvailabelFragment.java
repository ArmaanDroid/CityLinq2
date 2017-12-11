package fragments.passes;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.AvailablePassesAdapter;
import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dialog.PayByWalletDialog;
import fragments.MyBaseFragment;
import listners.AdapterItemClickListner;
import models.CommonPojo;
import models.Pass;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailabelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailabelFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<Pass> passList;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.no_record_text2)
    TextView no_record_text2;

    public AvailabelFragment() {
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
    public static AvailabelFragment newInstance(List<Pass> param1, String param2) {
        AvailabelFragment fragment = new AvailabelFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            passList = getArguments().getParcelableArrayList(ARG_PARAM1);
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
    }

    private void initView() {
        if (passList.size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new AvailablePassesAdapter(passList, new AdapterItemClickListner() {
                @Override
                public void onClick(int position, String tag) {
                    final Pass itemSelected = passList.get(position);
                    PayByWalletDialog payByWalletDialog = new PayByWalletDialog(String.valueOf(itemSelected.getAmount()), new AdapterItemClickListner() {
                        @Override
                        public void onClick(int position, String tag) {
                            initiatePamentByWallet(itemSelected);
                        }
                    });
                    payByWalletDialog.show(getFragmentManager(), "PayByWallet");
                }
            }));

            no_record_text2.setVisibility(View.GONE);
        } else {
            no_record_text2.setVisibility(View.VISIBLE);
        }

    }

    private void initiatePamentByWallet(final Pass pass) {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setAmount(String.valueOf(pass.getAmount()));
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setRequestEndPoint(RequestEndPoints.PAY_BY_WALLET);
        updateData(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                buyPass(pass);
            }

            @Override
            public void failure() throws Exception {
                showToast("Payment Failed");
            }
        });
    }

    private void buyPass(Pass pass) {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.PURCHASE_PASS);
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setPayment(String.valueOf(pass.getAmount()));
        webRequestData.setExpiaryDate(String.valueOf(getExpiryDate(pass.getValidity())));
        webRequestData.setPassId(pass.getId());
        webRequestData.setRouteId(pass.getRoute().getId());

        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                EventBus.getDefault().post(new PassBoughtEvent(commonPojo.getPass()));
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }


    private long getExpiryDate(String validity){
        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(validity));
        return calendar.getTimeInMillis();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
