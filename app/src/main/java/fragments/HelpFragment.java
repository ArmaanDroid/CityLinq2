package fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private Intent intentLoginSignup;

    @BindView(R.id.textViewPrivacyPolicy)
    TextView textViewPrivacyPolicy;

    @BindView(R.id.textViewTerms)
    TextView textViewTerms;

    @BindView(R.id.textViewAboutUs)
    TextView textViewAboutUs;

    @BindView(R.id.textViewFaq)
    TextView textViewFaq;

    public HelpFragment() {
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
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.textViewFaq)
    void openFaqs() {
        FragTransactFucntion.replaceFragFromRightHistory(getFragmentManager()
                , WebViewFragment.newInstance(getString(R.string.faq_s), AppConstants.FAQS_LINK), R.id.frame_container_main);
    }

    @OnClick(R.id.textViewPrivacyPolicy)
    void textViewPrivacyPolicy() {
        FragTransactFucntion.replaceFragFromRightHistory(getFragmentManager()
                , WebViewFragment.newInstance(getString(R.string.privacy_policy), AppConstants.POLICY_LINK), R.id.frame_container_main);
    }

    @OnClick(R.id.textViewTerms)
    void textViewTerms() {
        FragTransactFucntion.replaceFragFromRightHistory(getFragmentManager()
                , WebViewFragment.newInstance(getString(R.string.terms_of_use), AppConstants.TERM_CONDITION_LINK), R.id.frame_container_main);
    }

    @OnClick(R.id.textViewAboutUs)
    void textViewAboutUs() {
        FragTransactFucntion.replaceFragFromRightHistory(getFragmentManager()
                , WebViewFragment.newInstance(getString(R.string.about_us), AppConstants.ABOUT_US_LINK), R.id.frame_container_main);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_HELP_FRAGMENT, "");

        textViewAboutUs.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_arrow_right), null);
        textViewFaq.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_arrow_right), null);
        textViewPrivacyPolicy.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_arrow_right), null);
        textViewTerms.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_arrow_right), null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
