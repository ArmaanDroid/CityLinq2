package fragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import models.Ticket;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.TRANSPARENT;

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

    private int WIDTH;

    public RecieptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1          Parameter 1.
     * @param destinationName Parameter 2.
     * @param sourceName
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecieptFragment newInstance(Ticket param1, String destinationName, String sourceName) {
        RecieptFragment fragment = new RecieptFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, destinationName);
        args.putString(ARG_PARAM3, sourceName);
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
        textViewBookingConfirmed.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_confirmed)
                , null, null, null);
        textViewDateBookTicket.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), R.drawable.ic_calendar_white), null, null, null);

        try {

            Date date = new Date();
            date.setTime(Long.parseLong(ticket.getDate()));

            textViewDestination.setText(destinationName);
            textViewSource.setText(sourceName);
            textViewDateBookTicket.setText(monthNameDateFormat.format(date));
            textViewTransportName.setText(ticket.getTransportName());
            textViewVehicleNumber.setText(ticket.getVehicleNumber());
            textViewTiming.setText(ticket.getTimings());

            imageQrCode.setImageBitmap(encodeAsBitmap(ticket.getQrCode()));
        } catch (WriterException e) {
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
                pixels[offset + x] = result.get(x, y) ? WHITE : TRANSPARENT;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_RECIEPT_FRAGMENT, "");
    }


    @OnClick(R.id.continueButton)
    void bookAnothertrip() {
        FragTransactFucntion.replaceFragFromFadeWithoutHistory(getFragmentManager(), new HomeFragment(), R.id.frame_container_main);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
