package fragments.passes;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import adapters.PassesPagerAdapter;
import adapters.SelectPassAdapter;
import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import listners.AdapterItemClickListner;
import models.CommonPojo;
import models.PurchasePass;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectPassFragment extends MyBaseFragment {
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
    @BindView(R.id.buttonLogin)
    Button button;

    ArrayList<PurchasePass> purchasePasses;
    private SelectPassAdapter mAdapter;

    public SelectPassFragment() {
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
    public static SelectPassFragment newInstance(String param1, String param2) {
        SelectPassFragment fragment = new SelectPassFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_pass, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_SELECT_PASS, "");
        purchasePasses = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SelectPassAdapter(purchasePasses, new AdapterItemClickListner() {
            @Override
            public void onClick(int position, String tag) {

            }
        });
        recyclerView.setAdapter(mAdapter);

        initView();
    }

    private void initView() {
        if (isNetworkConnected()) {
            no_record_text2.setVisibility(View.GONE);
            getDataServer();
        } else {
            showNoInternetConnection(no_record_text2);
            button.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void getDataServer() {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.GET_MY_PASSES + AppConstants.USER_ID);
        makeGetRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                if(commonPojo.getPurchasePasses()!=null) {
                    if(commonPojo.getPurchasePasses().size()<1){
                        showNoDataFound(no_record_text2);
                        button.setVisibility(View.GONE);
                        return;
                    }
                    button.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    purchasePasses.addAll(commonPojo.getPurchasePasses());
                    mAdapter.notifyDataSetChanged();
                }else{
                    showNoDataFound(no_record_text2);
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure() throws Exception {
                recyclerView.setVisibility(View.GONE);
                button.setVisibility(View.GONE);

                showServerDown(no_record_text2);
            }
        });
    }

    @OnClick(R.id.buttonLogin)
    void applyPass() {
        if (getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra("pass_id", purchasePasses.get(mAdapter.selectedItem).getId());
        getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);
        getFragmentManager().popBackStack();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener.changeUIAccToFragment(AppConstants.TAG_CHOOSE_PAYMENT_FRAGMENT, "");
        unbinder.unbind();
    }


}
