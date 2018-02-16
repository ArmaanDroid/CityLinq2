package fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareDialog;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private Intent intentLoginSignup;

    @BindView(R.id.textViewAddPromoCode)
    TextView textViewAddPromoCode;

    @BindView(R.id.textView6)
    TextView textViewPromoCode;

    public InviteFragment() {
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
    public static InviteFragment newInstance(String param1, String param2) {
        InviteFragment fragment = new InviteFragment();
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
        View view = inflater.inflate(R.layout.fragment_invite, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_INVITE_FRAGMENT, "");
        initView();
    }

    private void initView() {
        textViewPromoCode.setText(mPreference.getPromoCode());
        textViewAddPromoCode.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_right), null);
    }

    @OnClick(R.id.textViewAddPromoCode)
    void openAddFragment() {
        FragTransactFucntion.addFragFromRightFadeHistory(getFragmentManager(), new SubmitCodeFragment(), R.id.frame_container_main);
    }


    @OnClick(R.id.imageViewMessage)
    void shareCodeMoreOption() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, mPreference.getPromoCode());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @OnClick(R.id.imageViewFacebooks)
    void shareCodeFaceBook() {
        ShareFeedContent shareFeedContent = new ShareFeedContent.Builder().setLink("https://www.mycitylinq.com")
                .setLinkDescription("Promo Code : " + mPreference.getPromoCode())
                .build();

        ShareDialog shareDialog = new ShareDialog(getActivity());
        shareDialog.show(shareFeedContent, ShareDialog.Mode.FEED);
    }


    @OnClick(R.id.imageViewWhatsapp)
    void shareOnWhatsApp() {
        try {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mPreference.getPromoCode());
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("text/plain");

            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Please install whatsapp");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
