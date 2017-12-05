package fragments;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import adapters.BrowseLinqsAdapter;
import api.RequestEndPoints;
import api.RetrofitClient;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fragments.route_detail.RouteDetailFragment;
import listners.AdapterItemClickListner;
import models.CommonPojo;
import models.Station;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private Station stationSource;
    private Station stationDestination;
    private Unbinder unbinder;

    @BindView(R.id.recycleViewRoutes)
    RecyclerView recyclerView;

    @BindView(R.id.textViewSource)
    TextView textViewSource;

    @BindView(R.id.no_record_text)
    TextView no_record_text;

    @BindView(R.id.textViewDestination)
    TextView textViewDestination;

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
    public static BrowseLinqsFragment newInstance(Station param1, Station param2) {
        BrowseLinqsFragment fragment = new BrowseLinqsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stationSource = (Station) getArguments().getParcelable(ARG_PARAM1);
            stationDestination = (Station) getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse_linqs, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_BROWSE_LINQS_FRAGMENT, "");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

    }

    private void initViews() {

        textViewSource.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(),R.drawable.ic_source_location_white), null,null, null);
        textViewDestination.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(),R.drawable.ic_destination_white), null,null, null);
        textViewSource.setText(stationSource.getName());
        textViewDestination.setText(stationDestination.getName());

        //make routes request
        progressDialog.show();
        RetrofitClient.getRestClient().getRoutes(stationSource.getId(), stationDestination.getId()).enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {
                try {
                    progressDialog.dismiss();
                    final CommonPojo commonPojo = response.body();
                    if (commonPojo != null) {
                        if (commonPojo.getTransportList().size() > 0) {
                            no_record_text.setVisibility(View.GONE);
                        } else {
                            no_record_text.setVisibility(View.VISIBLE);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
                        recyclerView.setAdapter(new BrowseLinqsAdapter(commonPojo.getTransportList(), new AdapterItemClickListner() {
                            @Override
                            public void onClick(int position, String Tag) {
                                if (Tag.isEmpty())
                                    FragTransactFucntion.addFragFromFadeHistory(getFragmentManager(),
                                            RouteDetailFragment.newInstance(commonPojo.getTransportList().get(position).getRoute().getStations(), null), R.id.frame_container_main);
                                else
                                    FragTransactFucntion.addFragFromFadeHistory(getFragmentManager()
                                            , BookMyTripFragment.newInstance(stationSource,stationDestination,commonPojo.getTransportList().get(position)), R.id.frame_container_main);

                            }
                        }));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener.changeUIAccToFragment(AppConstants.TAG_HOME_FRAGMENT,"");
    }
}
