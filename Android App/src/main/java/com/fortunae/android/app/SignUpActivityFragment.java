package com.fortunae.android.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.FileNotFoundException;
import java.io.IOException;


public class SignUpActivityFragment extends Fragment {
    private static Bitmap IMAGE = null;
    private static Bitmap ROTATE_IMAGE = null;

    private ImageView mProfilePicImageView;
    private AutoCompleteTextView mFirstNameTextView;
    private AutoCompleteTextView mLastNameTextView;
    private AutoCompleteTextView mEmailAddressTextView;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mSignUpButton;

    public SignUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProfilePicImageView = (ImageView) view.findViewById(R.id.profile_pic);

        mFirstNameTextView = (AutoCompleteTextView) view.findViewById(R.id.firstName);
        mLastNameTextView = (AutoCompleteTextView) view.findViewById(R.id.lastName);

        mEmailAddressTextView = (AutoCompleteTextView) view.findViewById(R.id.email);
        mPasswordEditText = (EditText) view.findViewById(R.id.password);
        mConfirmPasswordEditText = (EditText) view.findViewById(R.id.confirm_password);

        mSignUpButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageUtils.REQUEST_CODE_GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();
            try {
                IMAGE = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                if (ImageUtils.getOrientation(getActivity().getApplicationContext(), mImageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(ImageUtils.getOrientation(getActivity().getApplicationContext(), mImageUri));
                    if (ROTATE_IMAGE != null)
                        ROTATE_IMAGE.recycle();
                    ROTATE_IMAGE = Bitmap.createBitmap(IMAGE, 0, 0, IMAGE.getWidth(), IMAGE.getHeight(), matrix,true);
                    mProfilePicImageView.setImageBitmap(ROTATE_IMAGE);
                } else
                    mProfilePicImageView.setImageBitmap(IMAGE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attempts to register the account specified by the sign up form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual sign up attempt is made.
     */
    private void attemptSignUp() {
        // Reset errors.
        mFirstNameTextView.setError(null);
        mLastNameTextView.setError(null);
        mEmailAddressTextView.setError(null);
        mPasswordEditText.setError(null);
        mConfirmPasswordEditText.setError(null);

        // Store values at the time of the login attempt.
        Bitmap profilePic = IMAGE == null ? ROTATE_IMAGE : IMAGE;
        if (profilePic != null) {
            byte[] profilePicByteArray = ImageUtils.convert(profilePic);
        }

        String firstName = mFirstNameTextView.getText().toString();
        String lastName = mLastNameTextView.getText().toString();
        String email = mEmailAddressTextView.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid first name.
        if (TextUtils.isEmpty(firstName)) {
            mFirstNameTextView.setError(getString(R.string.error_field_required));
            focusView = mFirstNameTextView;
            cancel = true;
        }

        // Check for a valid last name.
        if (TextUtils.isEmpty(lastName)) {
            mLastNameTextView.setError(getString(R.string.error_field_required));
            focusView = mLastNameTextView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailAddressTextView.setError(getString(R.string.error_field_required));
            focusView = mEmailAddressTextView;
            cancel = true;
        } else if (!StringUtils.isEmailValid(email)) {
            mEmailAddressTextView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailAddressTextView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !StringUtils.isPasswordValid(password)) {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordEditText;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError(getString(R.string.error_invalid_confirm_password));
            focusView = mConfirmPasswordEditText;
            cancel = true;
        }

        if (!cancel) {
            ParseUser newUser = new ParseUser();
            newUser.put("firstName", firstName);
            newUser.put("lastName", lastName);
            newUser.setEmail(email);
            newUser.setUsername(email);
            newUser.setPassword(password);

            // Send data to Parse.com for verification
            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // Show a simple Toast message upon successful registration
                        Toast.makeText(getActivity(),
                                "Successfully Signed up, logging you in...",
                                Toast.LENGTH_LONG).show();
                        // Send user to LauncherActivity.class
                        Intent intent = new Intent(getActivity(),
                                LauncherActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(),
                                e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }
    }
}
