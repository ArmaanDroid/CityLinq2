package fragments;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.braintreepayments.api.Json;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fragments.route_detail.MapViewFragment;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import models.Scheduled;
import models.Station;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.DataParser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackLinqsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackLinqsFragment extends MyBaseFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Scheduled scheduledTrip;
    private String mParam2;
    private Unbinder unbinder;

    private GoogleMap mMap;
    private LatLngBounds bounds;
    private Marker busLocationMarker;
    private static boolean mapDrawn;
    private ArrayList<Station> stationList = null;

    @BindView(R.id.textView35)
    TextView textViewTimings;
    @BindView(R.id.textView25)
    TextView textViewModelNumber;
    @BindView(R.id.textView22)
    TextView textViewVehicleName;
    private Socket socket;

    public TrackLinqsFragment() {
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
    public static TrackLinqsFragment newInstance(Scheduled param1, String param2) {
        TrackLinqsFragment fragment = new TrackLinqsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConstants.RELOAD_HOME = true;
        if (getArguments() != null) {
            scheduledTrip = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            stationList = (ArrayList<Station>) scheduledTrip.getRoute().getStations();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_linq, container, false);
        unbinder = ButterKnife.bind(this, view);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_TRACK_LINQ_FRAGMENT, "");
        textViewVehicleName.setText(scheduledTrip.getTransportName());
        textViewModelNumber.setText(scheduledTrip.getVehicleNumber());
        textViewTimings.setText(scheduledTrip.getVehicle_start_time());

        if (scheduledTrip.getTripId() != null) {
            initSocket();

            socket.on(scheduledTrip.getTripId(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("TAG", "call: " + args[0]);
                    updateMarker(args);

                }
            });
        }
    }

    private void updateMarker(final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject json = new JSONObject(String.valueOf(args[0]));
                    LatLng latLng = new LatLng(json.getDouble("latitude"), json.getDouble("longitude"));

                    if (mMap != null) {

                        if (busLocationMarker == null) {
                            busLocationMarker = mMap.addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pointer))
                                    .position(latLng));
                        } else {
                            busLocationMarker.setPosition(latLng);
                        }

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initSocket() {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            socket = IO.socket(AppConstants.BASE_URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {

            }

        }).on("event", new Emitter.Listener() {

            @Override
            public void call(Object... args) {

            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {

            }
        });
        socket.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mListener.changeUIAccToFragment(AppConstants.TAG_MY_TRIPS_FRAGMENT,"");
        if (socket != null)
            socket.disconnect();
    }

    public static int index;
    static ArrayList<LatLng> points;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        progressDialog.show();
        index = 0;
        points = new ArrayList<>();
        getLatLngMakeTrack(index++);
    }


    private void getLatLngMakeTrack(int position) {
        if (position == stationList.size() - 1) {
            progressDialog.dismiss();
            drawFinalPath();
            return;
        }
        Station stationStart = stationList.get(position);
        Station stationDest = stationList.get(position + 1);


        showTrack(new LatLng(stationStart.getLatitude(), stationStart.getLongitude())
                , new LatLng(stationDest.getLatitude(), stationDest.getLongitude()), stationStart.getName(), stationDest.getName()
                , stationStart.getDescription(), stationDest.getDescription());
    }

    private void drawFinalPath() {
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(points);
        lineOptions.width(10);
        lineOptions.color(ContextCompat.getColor(getActivity(), R.color.polylineColor));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : points)
            builder.include(latLng);
        LatLngBounds bounds = builder.build();

        mMap.addPolyline(lineOptions);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.animateCamera(cameraUpdate);
    }

    private void showTrack(LatLng latLngSource, LatLng latLngDestiation, String sourceName, String destinationName, String srcDesc, String destDesc) {

        if (index == 1) {
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.route_pointer))
                    .position(latLngSource).title(sourceName).snippet(srcDesc));
        }
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.route_pointer))
                .position(latLngDestiation).title(destinationName).snippet(destDesc));


// Getting URL to the Google Directions API
        String url = getUrl(latLngSource, latLngDestiation);

        FetchUrl FetchUrl = new FetchUrl();
        FetchUrl.execute(url);
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


            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {

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


                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }
            getLatLngMakeTrack(index++);
            // Drawing polyline in the Google Map for the i-th route

        }
    }

}
