package fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dialog.ErrorDialogFragment;
import events.TripCanceledEvent;
import fragments.trips.MyTripsFragment;
import models.CommonPojo;
import models.Scheduled;
import models.Ticket;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecieptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecieptFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private Ticket ticket;
    private String destinationName;
    private String sourceName;
    private Unbinder unbinder;

    @BindView(R.id.textViewBookingConfirmed)
    TextView textViewBookingConfirmed;

    @BindView(R.id.textViewDateBookTicket)
    TextView textViewDateBookTicket;

    @BindView(R.id.imageQrCode)
    ImageView imageQrCode;

    @BindView(R.id.textViewSource)
    TextView textViewSource;

    @BindView(R.id.textViewDestination)
    TextView textViewDestination;

    @BindView(R.id.textView25)
    TextView textViewVehicleNumber;

    @BindView(R.id.textView22)
    TextView textViewTransportName;

    @BindView(R.id.textViewTiming)
    TextView textViewTiming;

    @BindView(R.id.textViewTermsCondition)
    TextView textViewTermsCondition;

    @BindView(R.id.continueButton)
    Button continueButton;

    private int WIDTH;
    private boolean isConfirmTicket;
    private boolean isCompleteTrip;

    public RecieptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1           Parameter 1.
     * @param destinationName  Parameter 2.
     * @param sourceName
     * @param isCompleteTicket
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecieptFragment newInstance(Ticket param1, String destinationName, String sourceName, boolean confirmTicket, boolean isCompleteTicket) {
        RecieptFragment fragment = new RecieptFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, destinationName);
        args.putString(ARG_PARAM3, sourceName);
        args.putBoolean(ARG_PARAM4, confirmTicket);
        args.putBoolean(ARG_PARAM5, isCompleteTicket);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticket = getArguments().getParcelable(ARG_PARAM1);
            destinationName = getArguments().getString(ARG_PARAM2);
            sourceName = getArguments().getString(ARG_PARAM3);
            isConfirmTicket = getArguments().getBoolean(ARG_PARAM4);
            isCompleteTrip = getArguments().getBoolean(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reciept, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {

        if (isConfirmTicket) {
            textViewBookingConfirmed.setVisibility(View.VISIBLE);
            textViewTermsCondition.setVisibility(View.GONE);
            continueButton.setBackgroundColor(getColor(R.color.btnColorDark));

            continueButton.setText(R.string.book_another_trip);

            textViewBookingConfirmed.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_confirmed)
                    , null, null, null);
            textViewDateBookTicket.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_calendar_white), null, null, null);
        } else {

            textViewBookingConfirmed.setVisibility(View.GONE);
            textViewTermsCondition.setVisibility(View.VISIBLE);
            continueButton.setText(R.string.cancel_trip);
            continueButton.setBackgroundColor(getColor(R.color.pale_red));

            SpannableString spannableString = new SpannableString(getString(R.string.term_condition2));
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    try {

                        FragTransactFucntion.replaceFragFromRightHistory(getFragmentManager()
                                , WebViewFragment.newInstance(getString(R.string.term_condition2), AppConstants.TERM_CONDITION_LINK), R.id.frame_container_main);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, spannableString.length(), 0);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.black)), 0, spannableString.length(), 0);

            textViewTermsCondition.setText(TextUtils.concat(getString(R.string.term_condition1), spannableString)
            );
            textViewTermsCondition.setMovementMethod(LinkMovementMethod.getInstance());

            if (isCompleteTrip) {
                continueButton.setVisibility(View.GONE);
                textViewTermsCondition.setVisibility(View.GONE);
            }
        }
        try {

            Date date = new Date();
            date.setTime(Long.parseLong(ticket.getDate()));

            textViewDestination.setText(destinationName);
            textViewSource.setText(sourceName);
            textViewDateBookTicket.setText(monthNameDateFormat.format(date));
            textViewTransportName.setText(ticket.getTransportName());
            textViewVehicleNumber.setText(ticket.getVehicleNumber());
            textViewTiming.setText(ticket.getTimings());
//            Bitmap myBitmap = QRCode.from(ticket.getQrCode()).bitmap();
//            imageQrCode.setImageBitmap(myBitmap);
            imageQrCode.setImageBitmap(encodeAsBitmap(ticket.getQrCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            WIDTH = getResources().getDimensionPixelOffset(R.dimen.qr_code_dp);
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isConfirmTicket)
            mListener.changeUIAccToFragment(AppConstants.TAG_RECIEPT_FRAGMENT, "");
        else
            mListener.changeUIAccToFragment(AppConstants.TAG_VIEW_TRIP, "");
    }


    @OnClick(R.id.continueButton)
    void bookAnothertrip() {
        if (isConfirmTicket) {
            HomeFragment fragment = new HomeFragment();
            fragment.stationDestination = null;
            fragment.stationSource = null;
            AppConstants.setStations(null);
            FragTransactFucntion.replaceFragFromFadeWithoutHistory(getFragmentManager(), fragment, R.id.frame_container_main);
        } else {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Cancel Trip")
                    .setMessage("Do you want to cancel this trip?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            WebRequestData webRequestData = new WebRequestData();
                            webRequestData.setRequestEndPoint(RequestEndPoints.CANCEL_TRIP);
                            webRequestData.setTicket(String.valueOf(ticket.getTicket()));
                            webRequestData.setPayment(ticket.getPayment());
                            webRequestData.setBookId(ticket.getId());
                            webRequestData.setStarttime(ticket.getTimings());
                            updateData(webRequestData, new WeResponseCallback() {
                                @Override
                                public void onResponse(CommonPojo commonPojo) throws Exception {
                                    deleteFromAppConstants(ticket.getId());
                                    EventBus.getDefault().post(new TripCanceledEvent(ticket.getAdapterPosition(), ticket.getId()));
                                    getFragmentManager().popBackStack();
                                }

                                @Override
                                public void failure() throws Exception {

                                }
                            });
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
                    .show();
        }
    }

    private void deleteFromAppConstants(String id) {
        for (Scheduled scheduled : AppConstants.getScheduleList()) {
            if (scheduled.getId().equalsIgnoreCase(id)) {
                AppConstants.getScheduleList().remove(scheduled);
                break;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (getFragmentManager().findFragmentByTag(MyTripsFragment.class.getName()) != null)
            mListener.changeUIAccToFragment(AppConstants.TAG_MY_TRIPS_FRAGMENT, "");
    }


}
