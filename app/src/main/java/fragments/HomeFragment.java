package fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adapters.FavRideHomeAdapter;
import adapters.ScheduleRidesPagerAdapter;
import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dialog.DatePickerFragment;
import dialog.ErrorDialogFragment;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import listners.AdapterItemClickListner;
import models.City;
import models.CityList;
import models.CommonPojo;
import models.Scheduled;
import models.Station;
import models.Ticket;
import sanguinebits.com.citylinq.R;
import services.SingleShotLocationProvider;
import utils.AppConstants;
import utils.DataParser;
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
    private static final int PERMISSION_REQUEST_CODE = 1032;

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

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public static Station stationDestination;
    public static Station stationSource;
    private GoogleMap mMap;
    private Calendar calendar = Calendar.getInstance();
    private boolean newTrack;
    static ArrayList<LatLng> points = new ArrayList<>();
    private Marker myLocationMarker;
    private Marker markerDestination;
    private static Marker markerSource;
    private SimpleSearchDialogCompat selectCityDialog;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TAG", "onSaveInstanceState home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        newTrack = true;
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView();

        if (AppConstants.BRAINTREE_TOKEN == null)
            getBrainTreeToken();
    }

    private void getBrainTreeToken() {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.BRAIN_TREE_TOKEN);
        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                AppConstants.BRAINTREE_TOKEN = commonPojo.getToken();
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_HOME_FRAGMENT, "");

        if (mPreference.getDefaultCity() == null) {
            showSelectCityDialog();
            return;
        }

        if (!isLocationServiceEnabled()) {
            buildAlertMessageNoGps();
        } else {
            if (isGooglePlayServicesAvailable(getActivity()))
                progressDialog.show();
            mGetCurrentLocation();
        }
    }

    private void showSelectCityDialog() {
        if (selectCityDialog != null) {
            if (!selectCityDialog.isShowing())
                selectCityDialog.show();

            return;
        }

        final WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.EXPLORE_ROUTE);
        makeGetRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                selectCityDialog = new SimpleSearchDialogCompat(getActivity(), "Select City",
                        "Please select a city...", null, (ArrayList) commonPojo.getCityList(),
                        new SearchResultListener<CityList>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   final CityList item, int position) {
                                webRequestData.setRequestEndPoint(RequestEndPoints.MAKE_DEFAULT_CITIES);
                                webRequestData.setUserId(AppConstants.USER_ID);
                                webRequestData.setCity(item.getId());
                                updateData(webRequestData, new WeResponseCallback() {
                                    @Override
                                    public void onResponse(CommonPojo commonPojo) throws Exception {
                                        if (mPreference.getDefaultCityId() != null)
                                            if (mPreference.getDefaultCityId().equalsIgnoreCase(item.getId()))
                                                return;
                                        mPreference.setDefaultCity(item.getTitle());
                                        AppConstants.CITY_ID = item.getId();
                                        mPreference.setDefaultCityId(item.getId());
                                        showToast(item.getName() + " is selected as your default city. You can change it in profile settings later.");
                                        onResume();
                                    }

                                    @Override
                                    public void failure() throws Exception {
                                        showToast("Error connecting to the server...");
                                        onResume();
                                    }
                                });
                                dialog.dismiss();
                            }
                        });
                selectCityDialog.setCancelable(false);
                selectCityDialog.show();
            }


            @Override
            public void failure() throws Exception {

            }
        });
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    public void mGetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            progressDialog.dismiss();
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }
        SingleShotLocationProvider.requestSingleUpdate(getActivity(), new SingleShotLocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                AppConstants.CURRENT_LOCATION = location;
                progressDialog.dismiss();

                mGetStations();

            }
        });
    }

    private void intiView() {
        textViewJourneyDate.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_calendar_light), null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
        if (AppConstants.JOURNEY_DATE == null)
            textViewJourneyDate.setText(journeyDateFormat.format(calendar.getTime()));
        else
            textViewJourneyDate.setText(journeyDateFormat.format(AppConstants.JOURNEY_DATE));

        AppConstants.JOURNEY_DATE = calendar.getTime();

        if (stationDestination != null
                && stationSource != null) {
            buttonLinqs.setVisibility(View.VISIBLE);
            recyclerFavorite.setVisibility(View.GONE);
        }

        recyclerFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFavorite.setAdapter(new FavRideHomeAdapter());
    }

    public void mGetStations() {
        if (stationDestination != null & stationSource != null) {
            showTrack();
        } else if (mMap != null) {
            LatLng latLng = new LatLng(AppConstants.CURRENT_LOCATION.latitude, AppConstants.CURRENT_LOCATION.longitude);
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            myLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .title("You are here"));
        }

        //check if the stations list is available
        if (AppConstants.getStations() != null) {
            setAutoCompleteAdapters();
            return;
        }

        WebRequestData webRequestData = new WebRequestData();
//        webRequestData.setRequestEndPoint(RequestEndPoints.GET_BOOK_DATA + AppConstants.USER_ID + "?latitude=" + AppConstants.CURRENT_LOCATION.latitude + "&longitude=" + AppConstants.CURRENT_LOCATION.longitude+"&cityId="+AppConstants.CITY_ID);
        webRequestData.setRequestEndPoint(RequestEndPoints.GET_BOOK_DATA + AppConstants.USER_ID + "?cityId=" + AppConstants.CITY_ID + "&latitude=" + AppConstants.CURRENT_LOCATION.latitude + "&longitude=" + AppConstants.CURRENT_LOCATION.longitude);
        makeGetRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                AppConstants.WALLET_BALANCE = commonPojo.getWallet();
                AppConstants.setScheduleList(commonPojo.getSchedule());
                AppConstants.setStations(commonPojo.getStations());

                editTextDestination.setText("");
                editTextSource.setText("");
                if (commonPojo.getFavTrips().size() > 0) {
                    recyclerFavorite.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);

                } else {
                    recyclerFavorite.setVisibility(View.GONE);
                }

                setAutoCompleteAdapters();
            }

            @Override
            public void failure() throws Exception {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Unable to get data from server. Please try Again")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mGetStations();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                ;

                builder.show();
            }
        });
    }

    private void setAutoCompleteAdapters() {

        //set schedule adapter
        if (AppConstants.getScheduleList() != null) {
//            if (AppConstants.getScheduleList().size() > 0) {
//                viewPager.setVisibility(View.VISIBLE);
//                tabLayout.setVisibility(View.VISIBLE);
//                viewPager.setAdapter(new ScheduleRidesPagerAdapter(AppConstants.getScheduleList(), new AdapterItemClickListner() {
//                    @Override
//                    public void onClick(int position, String tag) {
//                        Scheduled currentTrip = AppConstants.getScheduleList().get(position);
//                        Ticket ticket = new Ticket();
//                        ticket.setId(currentTrip.getId());
//                        ticket.setQrCode(currentTrip.getQrCode());
//                        ticket.setDate(currentTrip.getDate());
//                        ticket.setTransportName(currentTrip.getTransportName());
//                        ticket.setVehicleNumber(currentTrip.getVehicleNumber());
//                        ticket.setTimings(currentTrip.getTimings());
//                        ticket.setPayment(currentTrip.getPayment());
//                        ticket.setTicket(currentTrip.getTicket());
//                        ticket.setAdapterPosition(position);
//                        FragTransactFucntion.replaceFragFromFadeHistory(getFragmentManager()
//                                , RecieptFragment.newInstance(ticket, currentTrip.getSource().getName()
//                                        , currentTrip.getDestination().getName(), false, false), R.id.frame_container_main);
//
//                    }
//                }));
//                tabLayout.setupWithViewPager(viewPager, true);
//            } else {
//                viewPager.setVisibility(View.GONE);
//            }
        } else {
            viewPager.setVisibility(View.GONE);
        }

        //check if adapter is already bound
        if (editTextDestination.getAdapter() != null && editTextSource.getAdapter() != null) {
            if (stationSource != null)
                return;
        }

        final ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(getContext(),
                android.R.layout.simple_dropdown_item_1line, AppConstants.getStations());
        editTextSource.setAdapter(adapter);

        if (stationSource == null) {
            editTextSource.setListSelection(0);
            stationSource = AppConstants.getStations().get(0);
        }

        final ArrayAdapter<Station> adapterDestinations = new ArrayAdapter<Station>(getContext(),
                R.layout.view_drop_down_main, AppConstants.getDestinationStations());

        editTextDestination.setAdapter(adapterDestinations);

        if (stationDestination != null & stationSource != null) {
            editTextDestination.setText(stationDestination.getName());
            editTextSource.setText(stationSource.getName());
        }

        editTextSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationSource = (Station) parent.getItemAtPosition(position);
                Log.d("TAG", "onItemClick: " + stationSource.getLatitude());
                AppConstants.getDestinationStations().clear();
                stationDestination = null;
                editTextDestination.setText("");
                getStationsAccordingTOSource(stationSource.getId());

                buttonLinqs.setVisibility(View.GONE);
                recyclerFavorite.setVisibility(View.GONE);
                newTrack = true;
                points.clear();
                hideSoftKeyboard(editTextSource);
                showSourceMarker();
//                    viewPager.setVisibility(View.GONE);

                showToast("Please pick a destination location");

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
                    newTrack = true;
                    points.clear();
                    hideSoftKeyboard(editTextDestination);
//                    viewPager.setVisibility(View.GONE);

                    showTrack();
                } else {
                    showToast("Please pick a source location");
                }
            }
        });
    }

    private void showSourceMarker() {
        if (mMap != null) {
            mMap.clear();

            LatLng latLngSource = new LatLng(stationSource.getLatitude(), stationSource.getLongitude());
            markerSource = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.route_pointer)).position(latLngSource).title(stationSource.getName()));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLngSource);
            mMap.animateCamera(cameraUpdate);

        }
    }

    private void getStationsAccordingTOSource(String id) {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.DESTINATION_STATIONS);
        webRequestData.setStation(id);
        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                if (commonPojo.getDestinationStations() == null) {
                    ErrorDialogFragment.newInstance(R.string.no_route, "No Shuttles from this station. Please select other origin. ").
                            show(getFragmentManager(), "errorDialog");
                    return;
                }
                if (commonPojo.getDestinationStations().size() == 0) {
                    ErrorDialogFragment.newInstance(R.string.no_route, "No Shuttles from this station. Please select other origin. ").
                            show(getFragmentManager(), "errorDialog");
                    return;
                }
                AppConstants.getDestinationStations().addAll(commonPojo.getDestinationStations());
                final ArrayAdapter<Station> adapterDestinations = new ArrayAdapter<Station>(getContext(),
                        R.layout.view_drop_down_main, AppConstants.getDestinationStations());

                editTextDestination.setAdapter(adapterDestinations);
                editTextDestination.showDropDown();
            }

            @Override
            public void failure() throws Exception {

            }
        });


    }

    private void showTrack() {
        if (!newTrack)
            return;

        newTrack = false;
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        LatLng latLngSource = new LatLng(stationSource.getLatitude(), stationSource.getLongitude());
        LatLng latLngDestiation = new LatLng(stationDestination.getLatitude(), stationDestination.getLongitude());


        markerSource = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.route_pointer)).position(latLngSource).title(stationSource.getName()));

        builder.include(latLngSource);

        markerDestination = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.trip_pointer)).position(latLngDestiation).title(stationDestination.getName()));
        builder.include(markerDestination.getPosition());

        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 70);
        mMap.animateCamera(cameraUpdate);

        if (points.size() > 0) {
            drawFinalPath();
            return;
        }
        // Getting URL to the Google Directions API
        String url = getUrl(latLngSource, latLngDestiation);
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl = new FetchUrl();
        FetchUrl.execute(url);
    }


    private void drawFinalPath() {
        PolylineOptions lineOptions = new PolylineOptions(); // Adding all the points in the route to LineOptions
        lineOptions.addAll(points);
        lineOptions.width(10);
        lineOptions.color(ContextCompat.getColor(getActivity(), R.color.polylineColor));
        mMap.addPolyline(lineOptions);
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points.clear();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }


            }

            // Drawing polyline in the Google Map for the i-th route
            drawFinalPath();

        }
    }

    @OnClick(R.id.imageViewToFro)
    void changeSourceDestination() {
        if (stationSource != null & stationDestination != null) {

            ErrorDialogFragment errorDialogFragment = ErrorDialogFragment.newInstanceWithCallBack(R.string.swap, "Do you want to swap Origin and Destination stations? ", new AdapterItemClickListner() {
                @Override
                public void onClick(int position, String tag) {
                    ErrorDialogFragment.clickListner = null;
                    Station tempStation = stationSource;
                    stationSource = stationDestination;
                    stationDestination = tempStation;

                    editTextSource.setText(stationSource.getName());
                    editTextDestination.setText(stationDestination.getName());

                    editTextSource.dismissDropDown();
                    editTextDestination.dismissDropDown();

                    LatLng latLngSource = new LatLng(stationSource.getLatitude(), stationSource.getLongitude());
                    LatLng latLngDestiation = new LatLng(stationDestination.getLatitude(), stationDestination.getLongitude());


                    markerSource = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.route_pointer)).position(latLngSource).title(stationSource.getName()));


                    markerDestination = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.trip_pointer)).position(latLngDestiation).title(stationDestination.getName()));


                }
            });

            errorDialogFragment.show(getFragmentManager(), "error_dialog");
        } else {
            showToast("Please select both origin and destination.");
        }
    }

    @OnClick(R.id.imageView7)
    void myLocation(View view) {
        hideSoftKeyboard(view);

        SingleShotLocationProvider.requestSingleUpdate(getActivity(), new SingleShotLocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                if (myLocationMarker != null)
                    myLocationMarker.remove();

                LatLng latLng = new LatLng(location.latitude, location.longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                myLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("You are here")
                );
            }
        });
    }

    @OnClick(R.id.textViewJourneyDate)
    void selectJourneyDate() {
        DatePickerFragment datePickerDialog = new DatePickerFragment(new DatePickerFragment.DateSelectionListner() {
            @Override
            public void onDateSelected(Date date) {
                textViewJourneyDate.setText(journeyDateFormat.format(date));
                AppConstants.JOURNEY_DATE = date;

            }
        });
        datePickerDialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.buttonLinqs)
    void browseLinqs() {
        FragTransactFucntion.addFragFromFadeHistory(getFragmentManager()
                , BrowseLinqsFragment.newInstance(stationSource, stationDestination), R.id.frame_container_main);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (AppConstants.CURRENT_LOCATION != null) {
            LatLng sydney = new LatLng(AppConstants.CURRENT_LOCATION.latitude, AppConstants.CURRENT_LOCATION.longitude);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mGetCurrentLocation();
                }
            } else {
                Toast.makeText(getActivity(), R.string.provide_permissions, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
