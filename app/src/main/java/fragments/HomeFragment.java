package fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;

import adapters.FavRideHomeAdapter;
import adapters.ScheduleRidesPagerAdapter;
import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dialog.DatePickerFragment;
import models.CommonPojo;
import models.Station;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends MyBaseFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private Intent intentLoginSignup;

    @BindView(R.id.scheduledTripLayout)
    ViewPager viewPager;

    @BindView(R.id.recyclerFavorite)
    RecyclerView recyclerFavorite;

    @BindView(R.id.textViewJourneyDate)
    TextView textViewJourneyDate;

    @BindView(R.id.editTextSource)
    AutoCompleteTextView editTextSource;

    @BindView(R.id.editTextDestination)
    AutoCompleteTextView editTextDestination;

    @BindView(R.id.buttonLinqs)
    Button buttonLinqs;

    private static Station stationDestination;
    private static Station stationSource;
    private GoogleMap mMap;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_HOME_FRAGMENT, "");
    }

    private void intiView() {
        textViewJourneyDate.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_calendar_light), null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);


        if (stationDestination != null
                && stationSource != null) {
            buttonLinqs.setVisibility(View.VISIBLE);
            recyclerFavorite.setVisibility(View.GONE);
        }

        viewPager.setAdapter(new ScheduleRidesPagerAdapter());
        recyclerFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFavorite.setAdapter(new FavRideHomeAdapter());
    }

    public void mGetStations() {
        LatLng latLng = new LatLng(AppConstants.CURRENT_LOCATION.latitude, AppConstants.CURRENT_LOCATION.longitude);
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.addMarker(new MarkerOptions().position(latLng)
                .title("You are here"));


        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.GET_BOOK_DATA + AppConstants.USER_ID + "?latitude=" + AppConstants.CURRENT_LOCATION.latitude + "&longitude=" + AppConstants.CURRENT_LOCATION.longitude);
        makeGetRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {

                if (commonPojo.getSchedule().size() > 0) {
                    viewPager.setVisibility(View.VISIBLE);

                } else {
                    viewPager.setVisibility(View.GONE);
                }
                if (commonPojo.getFavTrips().size() > 0) {
                    recyclerFavorite.setVisibility(View.VISIBLE);

                } else {
                    recyclerFavorite.setVisibility(View.GONE);
                }

                editTextSource.setText(commonPojo.getStations().get(0).getName());
                stationSource = commonPojo.getStations().get(0);

                final ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, commonPojo.getStations());
                editTextSource.setAdapter(adapter);
                editTextDestination.setAdapter(adapter);

                editTextSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        stationSource = (Station) parent.getItemAtPosition(position);
                        Log.d("TAG", "onItemClick: " + stationSource.getLatitude());

                        if (stationDestination != null) {
                            buttonLinqs.setVisibility(View.VISIBLE);
                            recyclerFavorite.setVisibility(View.GONE);
                            showTrack();
                        } else {
                            showToast("Please pick a destination location");
                        }
                    }
                });
                editTextDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        stationDestination = (Station) parent.getItemAtPosition(position);
                        Log.d("TAG", "onItemClick: " + stationDestination.getLatitude());
                        if (stationSource != null) {
                            buttonLinqs.setVisibility(View.VISIBLE);
                            recyclerFavorite.setVisibility(View.GONE);
                            showTrack();
                        } else {
                            showToast("Please pick a source location");
                        }
                    }
                });
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    private void showTrack() {
        mMap.clear();
        PolylineOptions polylineOPtion = new PolylineOptions();
        polylineOPtion.color(getColor(R.color.polylineColor));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        LatLng latLngSource = new LatLng(stationSource.getLatitude(), stationSource.getLongitude());
        LatLng latLngDestiation= new LatLng(stationDestination.getLatitude(), stationDestination.getLongitude());

            polylineOPtion.add(latLngSource);
            polylineOPtion.add(latLngDestiation);

            Marker marker= mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.route_pointer)).position(latLngSource));
            builder.include(marker.getPosition());

             Marker marker2= mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.trip_pointer)).position(latLngDestiation));
            builder.include(marker2.getPosition());

        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,50);
        Polyline polyline1 = mMap.addPolyline(polylineOPtion);
        mMap.animateCamera(cameraUpdate);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick(R.id.textViewJourneyDate)
    void selectJourneyDate() {
        DatePickerFragment datePickerDialog = new DatePickerFragment(new DatePickerFragment.DateSelectionListner() {
            @Override
            public void onDateSelected(Date date) {
                textViewJourneyDate.setText(journeyDateFormat.format(date));

            }
        });
        datePickerDialog.show(getFragmentManager(),"");
    }

    @OnClick(R.id.buttonLinqs)
    void browseLinqs() {
        FragTransactFucntion.addFragFromFadeHistory(getFragmentManager(), BrowseLinqsFragment.newInstance(stationSource, stationDestination), R.id.frame_container_main);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (AppConstants.CURRENT_LOCATION != null) {
            LatLng sydney = new LatLng(AppConstants.CURRENT_LOCATION.latitude, AppConstants.CURRENT_LOCATION.longitude);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }
    }
}
