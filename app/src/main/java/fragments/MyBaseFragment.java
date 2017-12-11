package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import api.RetrofitClient;
import api.WebRequestData;
import models.CommonPojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.AppPreference;


public class MyBaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OnFragmentInteractionListener mListener;
    public AppPreference mPreference;
    protected Dialog progressDialog;
    public Activity activity;
    public Calendar calendar=Calendar.getInstance();
    public MyBaseFragment() {
        // Required empty public constructor
    }
    protected DateFormat journeyDateFormat = new SimpleDateFormat("E, MMM d, yyyy");
    protected DateFormat monthNameDateFormat = new SimpleDateFormat("MMMM d, yyyy");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreference=new AppPreference(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new Dialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_background);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    protected int getColor(int colorCode) {
        return ContextCompat.getColor(getActivity(), colorCode);
    }
    protected void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
    public Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(getActivity(), drawableId);
    }
    private Typeface getFont(String assetName) {
        return Typeface.createFromAsset(getActivity().getAssets(), assetName);
    }

    protected int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    protected void setTypeFace(TextView tv, String fontName) {
        tv.setTypeface(getFont(fontName));
    }
    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public String setPriceAsText(String price){
        return "$"+price;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            activity=getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void changeUIAccToFragment(String fragmentTag, String s);
        void backPressed();

    }

    public interface WeResponseCallback {
        void onResponse(CommonPojo commonPojo) throws Exception;
        void failure() throws Exception;
    }

    /*web requests and stuff*/

    protected void makeRequest(final WebRequestData webRequestData,
                               final WeResponseCallback webResponseCallback) {
        progressDialog.show();
        Log.v("requesttttt", new Gson().toJson(webRequestData));
        Call<CommonPojo> call = RetrofitClient.getRestClient().makeRequest(webRequestData, webRequestData.getRequestEndPoint());
        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {
                try {
                    progressDialog.dismiss();
                    if (response.body().getMessage() != null && !response.body().getMessage().equalsIgnoreCase("Successful"))
                        showToast(response.body().getMessage());
                    if (response.isSuccessful() && response.body().isSuccess()) {
                        webResponseCallback.onResponse(response.body());
                    } else webResponseCallback.failure();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {
                try {
                    progressDialog.dismiss();
//                    showToast(t.toString());
                    webResponseCallback.failure();
                } catch (Exception e) {

                }
            }
        });
    }

    protected void makeGetRequest(final WebRequestData webRequestData,
                               final WeResponseCallback webResponseCallback) {
        progressDialog.show();
        Log.v("requesttttt", new Gson().toJson(webRequestData));
        Call<CommonPojo> call = RetrofitClient.getRestClient().makeGetRequest(AppConstants.BASE_URL+webRequestData.getRequestEndPoint());
        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {
                try {
                    progressDialog.dismiss();
                    if (response.body().getMessage() != null && !response.body().getMessage().equalsIgnoreCase("Successful"))
                        showToast(response.body().getMessage());
                    if (response.isSuccessful() && response.body().isSuccess()) {
                        webResponseCallback.onResponse(response.body());
                    } else webResponseCallback.failure();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {
                try {
                    progressDialog.dismiss();
//                    showToast(t.toString());
                    t.printStackTrace();
                    webResponseCallback.failure();
                } catch (Exception e) {

                }
            }
        });
    }
    protected void updateData(WebRequestData webRequestData,
                              final WeResponseCallback webResponseCallback) {
        progressDialog.show();
        Call<CommonPojo> call = RetrofitClient.getRestClient().updateData(webRequestData.getRequestEndPoint(), webRequestData);
        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {
                try {
                    progressDialog.dismiss();
                    if (response.body().getMessage() != null && !response.body().getMessage().equalsIgnoreCase("Successful"))
                        showToast(response.body().getMessage());
                    if (response.isSuccessful() && response.body().isSuccess()) {
                        webResponseCallback.onResponse(response.body());
                    } else webResponseCallback.failure();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {
                try {
                    progressDialog.dismiss();
                    // showToast(t.toString());
                    webResponseCallback.failure();
                } catch (Exception e) {

                }
            }
        });
    }

    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        getActivity().finish();
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
