package fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import models.CommonPojo;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import views.MyEditTextUnderline;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubmitCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitCodeFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.editTextPromoCode)
    MyEditTextUnderline editTextPromoCode;

    public SubmitCodeFragment() {
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
    public static SubmitCodeFragment newInstance(String param1, String param2) {
        SubmitCodeFragment fragment = new SubmitCodeFragment();
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
        View view = inflater.inflate(R.layout.fragment_submit_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeUIAccToFragment(AppConstants.TAG_SUBMIT_CODE_FRAGMENT,"");

    }

    @OnClick(R.id.buttonSubmit)
    void submitPromoCode(){
        String promoCode = editTextPromoCode.getText().toString().trim();
        if(promoCode.isEmpty()){
            editTextPromoCode.setError("Please enter the code");
            return;
        }

        WebRequestData webRequestData=new WebRequestData();
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setPromocode(promoCode);
        webRequestData.setRequestEndPoint(RequestEndPoints.USE_PROMO_CODE);

        updateData(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {

            }

            @Override
            public void failure() throws Exception {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener.changeUIAccToFragment(AppConstants.TAG_INVITE_FRAGMENT,"");
        unbinder.unbind();
    }


}
