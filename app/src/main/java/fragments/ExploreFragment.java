package fragments;


import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.ExploreRouteAdapter;
import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fragments.route_detail.RouteDetailFragment;
import listners.AdapterItemClickListner;
import models.CommonPojo;
import models.RouteList;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_record_text2)
    ImageView no_record_text2;
    private ArrayList<RouteList> routeList;
    private ExploreRouteAdapter adapter;


    public ExploreFragment() {
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
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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
        routeList = new ArrayList<RouteList>();

        adapter = new ExploreRouteAdapter(routeList, new AdapterItemClickListner() {
            @Override
            public void onClick(int position, String tag) {
                FragTransactFucntion.addFragFromFadeHistory(getFragmentManager()
                        , RouteDetailFragment.newInstance(routeList.get(position).getStations(), AppConstants.TAG_EXPLORE_FRAGMENT)
                        , R.id.frame_container_main);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_EXPLORE_FRAGMENT, "");
    }

    private void initViews() {

        if (isNetworkConnected()) {

            no_record_text2.setVisibility(View.GONE);
            getServerData();
        } else {
            showNoInternetConnection(no_record_text2);
            no_record_text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag().toString().equalsIgnoreCase(AppConstants.No_Internet))
                        initViews();
                }
            });
        }

    }

    private void getServerData() {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.EXPLORE_ROUTE);
        makeGetRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                routeList.addAll(commonPojo.getRouteList());
                adapter.notifyDataSetChanged();
                if (routeList.size() > 0)
                    no_record_text2.setVisibility(View.GONE);
                else
                    showNoDataFound(no_record_text2);
            }

            @Override
            public void failure() throws Exception {
                showServerDown(no_record_text2);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
