package fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import models.CommonPojo;
import models.PendingReviewtrip;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFleetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFleetFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private PendingReviewtrip reviewDetails;
    private String mParam2;
    private Unbinder unbinder;
    private Intent intentLoginSignup;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.textViewReaction)
    TextView textViewReaction;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.circleImageViewProfile)
    CircleImageView circleImageViewProfile;

    @BindView(R.id.textView13)
    TextView textViewAddedToFavourite;

    @BindView(R.id.textViewUserName)
    TextView textViewUserName;

    public ReviewFleetFragment() {
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
    public static ReviewFleetFragment newInstance(PendingReviewtrip param1, String param2) {
        ReviewFleetFragment fragment = new ReviewFleetFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reviewDetails = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewUserName.setText(reviewDetails.getDriver().getName());
        int pixels=getResources().getDimensionPixelOffset(R.dimen.profile_review);
        Picasso.with(getContext()).load(AppConstants.BASE_URL_image_driver + reviewDetails.getDriver().getProfilePic())
                .placeholder(R.drawable.user)
                .resize(pixels,pixels)
                .into(circleImageViewProfile);
        mListener.changeUIAccToFragment(AppConstants.TAG_REVIEW_FRAGMENT, "");
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//        recyclerView.setAdapter(new RatingAdapter());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 1) {
                    textViewReaction.setText("Hated it");
                } else if (rating == 2) {
                    textViewReaction.setText("Not good");
                } else if (rating == 3) {
                    textViewReaction.setText("Average");

                } else if (rating == 4) {
                    textViewReaction.setText("Good");

                } else if (rating == 5) {
                    textViewReaction.setText("Awesome");

                }
            }
        });
    }

    @OnClick(R.id.continueButton)
    void submitReview(){
      String comment=  editText.getText().toString();

        WebRequestData webRequestData=new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.REVIEW_DRIVER);
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setTripId(reviewDetails.getTripId());
        webRequestData.setVehicleId(reviewDetails.getVehicle());
        webRequestData.setDriverId(reviewDetails.getDriver().getId());
        webRequestData.setRating(String.valueOf(ratingBar.getRating()));
        webRequestData.setReview(comment);

        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                getFragmentManager().popBackStack();
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    @OnClick(R.id.textView13)
    void addTOFavorite() {
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.ADD_TO_FAVOURITE);
        webRequestData.setSource("");
        webRequestData.setTransportName("");
        webRequestData.setDestination("");
        webRequestData.setVehicleNumber("");
        webRequestData.setVehicleId("");
        webRequestData.setTime("");
        webRequestData.setBookId("");

        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                showAddedToFavorite();
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    private void showAddedToFavorite() {
        textViewAddedToFavourite.setText("Added to your favorites");
        textViewAddedToFavourite.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_confirmed), null, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
