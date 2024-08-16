package com.example.gemidex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import IRepository.UploadCloudinaryCallback;
import Model.ApiResponse;
import Model.GemiObject;
import Service.APIService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviewActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private TextView namePrw, descriptionPrw, weightPrw, heightPrw, speedPrw, categoryPrw ;
    private ImageView imageView, typeIcon, subTypeIcon;
    Map config = new HashMap();

    private void configCloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "dklkzeill");  // Replace with your Cloudinary cloud name
        config.put("api_key", "827655834549677");        // Replace with your Cloudinary API key
        config.put("api_secret", "f9BGy89C7Wp_HVrnuitK8POPjlE");  // Replace with your Cloudinary API secret
        config.put("secure", true);
        MediaManager.init(this, config);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        configCloudinary();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        imageView = findViewById(R.id.imageView);
        namePrw = findViewById(R.id.pokemon_name);
        typeIcon = findViewById(R.id.type_icon);
        subTypeIcon = findViewById(R.id.subtype_icon);
        descriptionPrw = findViewById(R.id.description);
        weightPrw = findViewById(R.id.weight_text);
        heightPrw = findViewById(R.id.height_text);
        speedPrw = findViewById(R.id.speed_text);
        categoryPrw = findViewById(R.id.category_text);
        readResultFromJson();
    }

    private void readResultFromJson() {
        File file = new File(getFilesDir(), "resultAPI.json");

        if (!file.exists()) {
            Log.e("ReadResult", "File does not exist: " + file.getAbsolutePath());
            return;
        }
        String jsonString = readJsonFromFile();

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        // Extract the nested JSON object under "result"
        String resultString = jsonObject.get("result").getAsString();
        resultString = resultString.replace("```json\n", "").replace("\n```", "").trim();
        JsonObject resultObject = JsonParser.parseString(resultString).getAsJsonObject();

        String type_raw = resultObject.get("type").getAsString().toLowerCase();
        String subType_raw = resultObject.has("subtype") && !resultObject.get("subtype").isJsonNull() ? resultObject.get("subtype").getAsString().toLowerCase() : null;

        String type = "type_icon_"+type_raw;

        String subType = subType_raw != null ? "type_icon_"+subType_raw : null;

        if(subType != null){
            int subTypeIdBackground = getResources().getIdentifier(subType, "drawable", getPackageName());
            subTypeIcon.setBackgroundResource(subTypeIdBackground);
            subTypeIcon.setVisibility(View.VISIBLE);

        }else{
            subTypeIcon.setVisibility(View.GONE);
        }

        int typeIdBackground = getResources().getIdentifier(type, "drawable", getPackageName());

        String gemiName = resultObject.get("object").getAsString();
        String description = resultObject.has("description") && !resultObject.get("description").isJsonNull() ? resultObject.get("description").getAsString() : "N/A";
        String weight = resultObject.get("approximateWeight").getAsString();
        String heigh = resultObject.get("approximateHeight").getAsString();
        String category = resultObject.get("species").getAsString();
        String speed = resultObject.has("speed") && !resultObject.get("speed").isJsonNull() ? String.valueOf(resultObject.get("speed").getAsInt()) : "N/A";

        // Set text to TextViews
        namePrw.setText(gemiName);
        typeIcon.setBackgroundResource(typeIdBackground);
        descriptionPrw.setText(description);
        weightPrw.setText(weight);
        heightPrw.setText(heigh);
        speedPrw.setText(speed);
        categoryPrw.setText(category);

        String imagePath = getIntent().getStringExtra("imagePath");
        File imgFile = null;
        if (imagePath != null) {
            imgFile = new File(imagePath);
            if(imgFile.exists()){
                Bitmap img = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(img);  // Only change the image source

            }
        }
        uploadImage(imgFile, new UploadCloudinaryCallback() {
            @Override
            public void onSuccess(String url) {
                // Image upload is successful, now you can use the URL
                String imgURL = url;

                // Get the GoogleSignInAccount
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(PreviewActivity.this);
                if (acct != null) {
                    // Create the GemiObject with the URL
                    GemiObject gemiObject = new GemiObject(acct.getId(), type_raw, subType_raw, gemiName, description, weight, heigh, category, speed, imgURL);

                    // Save the GemiObject
                    saveGemiObject(gemiObject);
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
                Log.e("UploadError", errorMessage);
            }
        });
    }

    void saveGemiObject(GemiObject gemiObject) {
        APIService.apiService.createGemidexEntry(gemiObject).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse.getIsSuccess()) {
                        Toast.makeText(PreviewActivity.this, "Save successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PreviewActivity.this, "Save failed: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PreviewActivity.this, "Save failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(PreviewActivity.this, "Send post error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadImage(File imgFile, UploadCloudinaryCallback callback) {
        MediaManager.get().upload(imgFile.getAbsolutePath())
                .option("folder", "Gemidex")
                .callback(new com.cloudinary.android.callback.UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        // Upload started
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        // Upload in progress
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        // Upload successful
                        String url = (String) resultData.get("url");
                        callback.onSuccess(url);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        // Upload failed
                        callback.onError(error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // Upload rescheduled
                    }
                }).dispatch();
    }



    private String readJsonFromFile() {
        File file = new File(getFilesDir(), "resultAPI.json");
        StringBuilder jsonContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonContent.toString();
    }
}
