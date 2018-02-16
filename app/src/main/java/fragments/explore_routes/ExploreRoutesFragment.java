package fragments.explore_routes;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import adapters.ExploreRouteAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import fragments.route_detail.RouteDetailFragment;
import listners.AdapterItemClickListner;
import models.RouteList;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreRoutesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreRoutesFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    ;
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_record_text2)
    ImageView no_record_text2;
    private ExploreRouteAdapter adapter;
    ArrayList<RouteList> routeList;

    public ExploreRoutesFragment() {
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
    public static ExploreRoutesFragment newInstance(List<RouteList> param1, String param2) {
        ExploreRoutesFragment fragment = new ExploreRoutesFragment();
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
            routeList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_with_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new ExploreRouteAdapter(routeList, new AdapterItemClickListner() {
            @Override
            public void onClick(int position, String tag) {
                FragTransactFucntion.addFragFromFadeHistory(getFragmentManager()
                        , RouteDetailFragment.newInstance(routeList.get(position).getStations(), AppConstants.TAG_EXPLORE_FRAGMENT)
                        , R.id.frame_container_main);
            }
        });

        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_EXPLORE_FRAGMENT, "");
    }
    private void initView() {
        if (routeList == null) {
            showNoDataFound(no_record_text2);
            return;
        }
        if (routeList.size() == 0) {
            showNoDataFound(no_record_text2);
            return;
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_HOME_FRAGMENT, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mListener.changeUIAccToFragment(AppConstants.TAG_SELECT_CITY_FRAGMENT,"");
    }


}
