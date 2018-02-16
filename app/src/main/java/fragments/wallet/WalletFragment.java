package fragments.wallet;


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

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import models.CommonPojo;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;
import views.MyEditTextUnderline;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private Intent intentLoginSignup;

    @BindView(R.id.textViewTransactionHistory)
    TextView textViewTransactionHistory;

    @BindView(R.id.textViewAvailableBalance)
    TextView textViewAvailableBalance;

    @BindView(R.id.textView11)
    MyEditTextUnderline editTextAmount;
    private String availableAmount;
    private int REQUEST_CODE_paymemt=9087;
    private String amount;

    public WalletFragment() {
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
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
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
        mListener.changeUIAccToFragment(AppConstants.TAG_WALLET_FRAGMENT,"");
        availableAmount= String.valueOf(AppConstants.WALLET_BALANCE);
        initView();
    }

    private void initView() {
        if(AppConstants.WALLET_BALANCE!=null)
        textViewAvailableBalance.setText(setPriceAsText(availableAmount));
       else
            textViewAvailableBalance.setText("Not Available");

        textViewTransactionHistory.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(),R.drawable.ic_arrow_right), null);
    }

    @OnClick(R.id.textViewTransactionHistory)
    void transactionHistory(){
        FragTransactFucntion.addFragFromRightFadeHistory(getFragmentManager(),new TransactionHistoryFragment(),R.id.frame_container_main);
    }

    @OnClick(R.id.continueButton)
    void addMoney(){
       amount = editTextAmount.getText().toString().trim();
        if(amount.isEmpty() || amount.equalsIgnoreCase("0")){
            editTextAmount.setError(getString(R.string.please_enter_amount));
            return;
        }else if(Integer.valueOf(amount)<10){
            showToast("Amount can't be less than $10");
            return;
        }
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(AppConstants.BRAINTREE_TOKEN);

        dropInRequest.amount(amount);
        startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE_paymemt);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_paymemt) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                sendNonceToServer(result);
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    private void sendNonceToServer(DropInResult result) {
        WebRequestData webRequestData=new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.BRAIN_TREE_PAYMENT);
//        webRequestData.setNounce(result.getPaymentMethodNonce().getNonce());
        webRequestData.setNounce("fake-valid-nonce");
        webRequestData.setAmount(amount);
        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                if(commonPojo.getTransactionId()!=null){
                    addVerifiedMoneyToWallet(amount);
                }
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    private void addVerifiedMoneyToWallet(final String amount) {
        WebRequestData webRequestData=new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.ADD_TO_WALLET);
        webRequestData.setUserId(AppConstants.USER_ID);
        webRequestData.setAmount(amount);
        updateData(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                editTextAmount.setText("");
                AppConstants.WALLET_BALANCE = AppConstants.WALLET_BALANCE + Integer.valueOf(amount);
                textViewAvailableBalance.setText(setPriceAsText(String.valueOf(AppConstants.WALLET_BALANCE)));
                showToast("$"+amount+" credited to your account");
            }

            @Override
            public void failure() throws Exception {
                showToast("Error adding amount to your wallet");

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
