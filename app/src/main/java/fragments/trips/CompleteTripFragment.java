package fragments.trips;


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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.CompletedTripsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import fragments.RecieptFragment;
import listners.AdapterItemClickListner;
import models.Completed;
import models.Scheduled;
import models.Ticket;
import sanguinebits.com.citylinq.R;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompleteTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompleteTripFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Completed> tripList;
    private String mParam2;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_record_text2)
    ImageView no_record_text2;

    public CompleteTripFragment() {
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
    public static CompleteTripFragment newInstance(List<Completed> param1, String param2) {
        CompleteTripFragment fragment = new CompleteTripFragment();
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
            tripList = getArguments().getParcelableArrayList(ARG_PARAM1);
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
        initViews();
    }

    private void initViews() {
        if(tripList.size()>0){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(new CompletedTripsAdapter(getContext(),tripList,new AdapterItemClickListner() {
                @Override
                public void onClick(int position, String tag) {
                    Completed currentTrip = tripList.get(position);

                    Ticket ticket = new Ticket();
                    ticket.setId(currentTrip.getId());
                    ticket.setQrCode(currentTrip.getQrCode());
                    ticket.setDate(currentTrip.getDate());
                    ticket.setTransportName(currentTrip.getTransportName());
                    ticket.setVehicleNumber(currentTrip.getVehicleNumber());
                    ticket.setTimings(currentTrip.getVehicle_start_time());
                    ticket.setPayment(currentTrip.getPayment());
                    ticket.setTicket(currentTrip.getTicket());
                    ticket.setAdapterPosition(position);

                    FragTransactFucntion.addFragFromFadeHistory(getParentFragment().getFragmentManager()
                            , RecieptFragment.newInstance(ticket, currentTrip.getSource().getName()
                                    , currentTrip.getDestination().getName(), false,true), R.id.frame_container_main);

                }
            }));
            no_record_text2.setVisibility(View.GONE);
        }else
            showNoDataFound(no_record_text2);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
