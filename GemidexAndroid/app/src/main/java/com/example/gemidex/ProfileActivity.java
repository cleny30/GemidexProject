package com.example.gemidex;

import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import IRepository.UserProfileCallback;
import Model.ApiResponse;
import Model.UserObject;
import Service.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ProfileActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView name;
    ImageView signOutBtn,snapBtn;
    private FrameLayout frameLayout;
    ImageView profileImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        frameLayout = findViewById(R.id.frameOutline);
        name = findViewById(R.id.name);
        profileImg  = findViewById(R.id.profile_img);
        signOutBtn = findViewById(R.id.signOut);
        snapBtn = findViewById(R.id.snapBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        clipFrameLayoutToCircle();
        if(acct!=null){
            String email = acct.getEmail();
            String fullName = acct.getDisplayName();
            String googleId = acct.getId();
            Uri imageURI = acct.getPhotoUrl();

            UserObject userObject = new UserObject(email,fullName,googleId);
            loadData(fullName, imageURI);

            getUserProfile(acct.getId(), new UserProfileCallback() {
                @Override
                public void onResult(boolean isRegistered) {
                    if (isRegistered) {
                        // Handle the case where the user is already registered
                        Toast.makeText(ProfileActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the case where the user is not registered
                        userResisterSendPost(userObject);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    // Handle the error
                }
            });
        }

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }

    void loadData(String personName, Uri imageURI){
        name.setText(personName);
        if (imageURI != null) {
            Glide.with(this)
                    .load(imageURI)
                    .placeholder(R.drawable.default_profile_picture) // Optional: placeholder while loading
                    .error(R.drawable.default_profile_picture) // Optional: fallback image in case of an error
                    .into(profileImg);
        } else {
            // Handle case when there is no profile picture
            profileImg.setImageResource(R.drawable.default_profile_picture); // Set a default image
        }
    }

    private void clipFrameLayoutToCircle() {
        frameLayout.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = Math.min(view.getWidth(), view.getHeight());
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        frameLayout.setClipToOutline(true);
    }

    void userResisterSendPost(UserObject user){
        APIService.apiService.register(user).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse.getIsSuccess()) {
                        Toast.makeText(ProfileActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Register failed: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Register failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Send post error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserProfile(String googleId, UserProfileCallback callback) {
        APIService.apiService.getUserById(googleId).enqueue(new Callback<ApiResponse<UserObject>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserObject>> call, Response<ApiResponse<UserObject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<UserObject> apiResponse = response.body();
                    if (apiResponse.getResult() != null) {
                        // User is already registered
                        callback.onResult(true);
                    } else {
                        // User is not registered
                        callback.onResult(false);
                    }
                } else {
                    // Handle unsuccessful response
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserObject>> call, Throwable t) {
                // Handle failure
                callback.onError(t);
            }
        });
    }

}
