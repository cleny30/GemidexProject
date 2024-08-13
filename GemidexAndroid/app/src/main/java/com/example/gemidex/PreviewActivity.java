package com.example.gemidex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Model.GemiObject;

public class PreviewActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private TextView namePrw;
    private TextView descriptionPrw;
    private TextView weightPrw;
    private TextView heightPrw;
    private TextView speedPrw;
    private TextView categoryPrw;
    private ImageView imageView;
    private ImageView typeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        imageView = findViewById(R.id.imageView);
        namePrw = findViewById(R.id.pokemon_name);
        typeIcon = findViewById(R.id.type_icon);
        descriptionPrw = findViewById(R.id.description);
        weightPrw = findViewById(R.id.weight_text);
        heightPrw = findViewById(R.id.height_text);
        speedPrw = findViewById(R.id.speed_text);
        categoryPrw = findViewById(R.id.category_text);
        readResultFromJson();

        String imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            loadImageFromPath(imagePath);
        }
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
        String bg = "type_icon_"+resultObject.get("type").getAsString().toLowerCase();

        int resId = getResources().getIdentifier(bg, "drawable", getPackageName());

        String typeId = resultObject.get("type").getAsString().toLowerCase();
        String gemiName = resultObject.get("object").getAsString();
        String description = resultObject.has("description") && !resultObject.get("description").isJsonNull() ? resultObject.get("description").getAsString() : "N/A";
        String weight = resultObject.get("approximateWeight").getAsString();
        String heigh = resultObject.get("approximateHeight").getAsString();
        String category = resultObject.get("species").getAsString();
        String speed = resultObject.has("speed") && !resultObject.get("speed").isJsonNull() ? String.valueOf(resultObject.get("speed").getAsInt()) : "N/A";

        // Set text to TextViews
        namePrw.setText(gemiName);
        typeIcon.setBackgroundResource(resId);
        descriptionPrw.setText(description);
        weightPrw.setText(weight);
        heightPrw.setText(heigh);
        speedPrw.setText(speed);
        categoryPrw.setText(category);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!= null){
            GemiObject gemiObject = new GemiObject(speed,category,heigh,weight,description,gemiName,typeId);
            String email =  acct.getEmail();
            saveGemiObject(gemiObject, email);
        }
    }

    private void saveGemiObject(GemiObject gemiObject, String email){

    }

    private void loadImageFromPath(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);  // Only change the image source
        }
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
    public class ResultData {
        private String object;
        private String type;
        private String description;
        private String approximateWeight;
        private String approximateHeight;
        private String category;
        private String speed;

        public ResultData(String object, String type, String description, String approximateWeight, String approximateHeight, String category, String speed) {
            this.object = object;
            this.type = type;
            this.description = description;
            this.approximateWeight = approximateWeight;
            this.approximateHeight = approximateHeight;
            this.category = category;
            this.speed = speed;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getApproximateWeight() {
            return approximateWeight;
        }

        public void setApproximateWeight(String approximateWeight) {
            this.approximateWeight = approximateWeight;
        }

        public String getApproximateHeight() {
            return approximateHeight;
        }

        public void setApproximateHeight(String approximateHeight) {
            this.approximateHeight = approximateHeight;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }
    }
}
