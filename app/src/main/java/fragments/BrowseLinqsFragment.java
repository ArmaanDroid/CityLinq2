package fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import adapters.BrowseLinqsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fragments.route_detail.RouteDetailFragment;
import listners.AdapterItemClickListner;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseLinqsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseLinqsFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.recycleViewRoutes)
    RecyclerView recyclerView;

    public BrowseLinqsFragment() {
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
    public static BrowseLinqsFragment newInstance(String param1, String param2) {
        BrowseLinqsFragment fragment = new BrowseLinqsFragment();
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
        View view = inflater.inflate(R.layout.fragment_browse_linqs, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));

        recyclerView.setAdapter(new BrowseLinqsAdapter(new AdapterItemClickListner() {
            @Override
            public void onClick(int position, String Tag) {
                if (Tag.isEmpty())
                    FragTransactFucntion.replaceFragFromFadeHistory(getFragmentManager(), new RouteDetailFragment(), R.id.frame_container_main);
                else
                    FragTransactFucntion.replaceFragFromFadeHistory(getFragmentManager(), new BookMyTripFragment(), R.id.frame_container_main);

            }
        }));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_BROWSE_LINQS_FRAGMENT, "");
    }

    @OnClick(R.id.imageViewMenu)
    void onBackNavigate() {
        mListener.backPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
