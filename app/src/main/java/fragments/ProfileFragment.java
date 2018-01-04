package fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import models.CommonPojo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import sanguinebits.com.citylinq.R;
import sanguinebits.com.citylinq.SplashActivity;
import utils.AppConstants;
import utils.GeneralFunctions;
import utils.GetSampledImage;
import views.MyEditTextUnderline;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends MyBaseFragment implements GetSampledImage.SampledImageAsyncResp {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1003;
    private static final int REQUEST_CODE_GALLERY = 2012;
    private static final int REQUEST_CODE_CAMERA = 2013;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.editTextName)
    MyEditTextUnderline editTextEmail;

    @BindView(R.id.editTextPhone)
    MyEditTextUnderline editTextPhone;

    @BindView(R.id.textViewUserName)
    EditText textViewUserName;

    @BindView(R.id.imageView9)
    ImageView imageView9;

    @BindView(R.id.circleImageViewProfile)
    ImageView circleImageViewProfile;

    private String picturePath;
    private Uri newImagePath;
    private File imageFile;

    public ProfileFragment() {
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
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        editTextEmail.setText(mPreference.getEmail());
        editTextPhone.setText(mPreference.getMobileNumber());
        textViewUserName.setText(mPreference.getName());
        Picasso.with(getContext()).load(AppConstants.BASE_URL_image + mPreference.getProfilePic())
                .placeholder(R.drawable.user)
                .into(circleImageViewProfile);
    }

    @OnClick(R.id.textViewEditProfile)
    void editOrSaveProfile(final View view) {
        if (view.getTag().toString().equalsIgnoreCase("edit")) {
            ((TextView) view).setText("Save Profile");
            view.setTag("");
            textViewUserName.setEnabled(true);
            imageView9.setVisibility(View.VISIBLE);
        } else {

            String name = textViewUserName.getText().toString().trim();

            if (name.isEmpty()) {
                showToast(getString(R.string.please_enter_name));
                return;
            }
            progressDialog.show();
            WebRequestData webRequestData = new WebRequestData();
            webRequestData.setName(name);
            webRequestData.setUserId(AppConstants.USER_ID);
            MultipartBody.Part body = null;
            if (imageFile != null) {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                body = MultipartBody.Part.createFormData("pic", imageFile.getName(), requestFile);
            }

            RequestBody token =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, new Gson().toJson(webRequestData));
            updateProfile(RequestEndPoints.UPDATE_PROFILE, token, body, new WeResponseCallback() {
                @Override
                public void onResponse(CommonPojo commonPojo) throws Exception {
                    showToast("Profile Updated Successfully");
                    ((TextView) view).setText("Edit Profile");
                    view.setTag("edit");
                    textViewUserName.setEnabled(false);
                    imageView9.setVisibility(View.GONE);
                    mPreference.setName(commonPojo.getUser().getName());
                    mPreference.setProfilePic(commonPojo.getUser().getProfilePic());
                }

                @Override
                public void failure() throws Exception {
                    showToast("Error Updating profile");
                }
            });


        }
    }

    @OnClick(R.id.buttonLogin)
    void logout(){
        progressDialog.show();
        LoginManager.getInstance().logOut();
        AppConstants.USER_ID=null;
        mPreference.setUserID(null);
        Intent intent=new Intent(getActivity(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.circleImageViewProfile)
    void chooseImage(View view) {
        if (textViewUserName.isEnabled())
            openPopUp((ImageView) view);
    }

    void openPopUp(ImageView imageView) {
        boolean permission = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !permission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            showPopUpMenu(imageView);
        }
    }

    private void showPopUpMenu(final ImageView imageView) {
        PopupMenu mMenu = new PopupMenu(getActivity(), imageView);

        mMenu.getMenuInflater().inflate(R.menu.popup_image_selection, mMenu.getMenu());

        mMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = null;
                switch (item.getItemId()) {
                    case R.id.menu_gallery:
                        i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media
                                        .EXTERNAL_CONTENT_URI);
                        startActivityForResult(i,
                                REQUEST_CODE_GALLERY);
                        break;

                    case R.id.menu_camera:
                        Intent takePictureIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = null;
                        try {
                            f = GeneralFunctions.setUpImageFile(AppConstants
                                    .LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS);
                            picturePath = f.getAbsolutePath();
                            Uri uri = FileProvider.getUriForFile(getActivity()
                                    , getActivity().getPackageName() + ".fileprovider"
                                    , f);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                            f = null;
                            picturePath = null;
                        }
                        startActivityForResult(takePictureIntent,
                                REQUEST_CODE_CAMERA);
                        break;
                }
                return false;
            }
        });
        mMenu.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_PROFILE_FRAGMENT, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA ||
                requestCode == REQUEST_CODE_GALLERY) {
            try {
                if (resultCode == Activity.RESULT_OK) {
                    boolean isGalleryImage = false;
                    if (requestCode == REQUEST_CODE_GALLERY) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        isGalleryImage = true;
                    }
                    progressDialog.show();
                    new GetSampledImage(this).execute(picturePath,
                            AppConstants.LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS,
                            String.valueOf(isGalleryImage),
                            String.valueOf((int) getResources().getDimension(R.dimen.h_100)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getActivity(), R.string.provide_permissions, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onSampledImageAsyncPostExecute(File file) {
        imageFile = file;
        if (imageFile != null) {
            newImagePath = Uri.parse(AppConstants.LOCAL_FILE_PREFIX + imageFile);
            circleImageViewProfile.setImageURI(newImagePath);
        }

        progressDialog.dismiss();

    }
}
