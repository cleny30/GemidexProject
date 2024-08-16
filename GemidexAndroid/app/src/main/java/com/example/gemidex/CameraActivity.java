package com.example.gemidex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.ai.client.generativeai.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Executor;


public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";
    private TextureView textureView;
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private FrameLayout frameLayout;
    private FrameLayout textCameraLayout, loading;

    private final String[] permissions = {Manifest.permission.CAMERA};
    private final int REQUEST_CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        textureView = findViewById(R.id.camera_lens);
        frameLayout = findViewById(R.id.button);

        textureView.setSurfaceTextureListener(textureListener);
        textCameraLayout  = findViewById(R.id.camera_text);
        loading  = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        ImageButton snapBtn = findViewById(R.id.snapBtn);
        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCameraLayout.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                captureAndSendImage();
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION);
        } else {
            setupCamera();
        }

        clipFrameLayoutToCircle();
    }

    private final TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            Log.d(TAG, "SurfaceTexture available");
            setupCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
            // Handle size change if needed
            Log.d(TAG, "SurfaceTexture size changed");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
            Log.d(TAG, "SurfaceTexture destroyed");
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
            // Update the preview if needed
            Log.d(TAG, "SurfaceTexture updated");
        }
    };

    private void setupCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION);
            return;
        }

        CameraManager manager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0];
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "CameraAccessException", e);
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException: Permission not granted", e);
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.d(TAG, "CameraDevice opened");
            cameraDevice = camera;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.d(TAG, "CameraDevice disconnected");
            camera.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.e(TAG, "CameraDevice error: " + error);
            camera.close();
            cameraDevice = null;
        }
    };

    private void createCameraPreviewSession() {
        SurfaceTexture texture = textureView.getSurfaceTexture();
        if (texture == null) {
            Log.e(TAG, "SurfaceTexture is null");
            return;
        }

        try {
            texture.setDefaultBufferSize(textureView.getWidth(), textureView.getHeight());
            Surface surface = new Surface(texture);

            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    captureSession = session;
                    try {
                        captureBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
                        captureSession.setRepeatingRequest(captureBuilder.build(), null, null);
                        Log.d(TAG, "CaptureSession configured and repeating request set");
                    } catch (CameraAccessException e) {
                        Log.e(TAG, "CameraAccessException", e);
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Log.e(TAG, "CaptureSession configuration failed");
                }
            }, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "CameraAccessException", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCamera();
            } else {
                Log.e(TAG, "Camera permission denied");
            }
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

    private void captureAndSendImage() {
        if (textureView.isAvailable()) {
            Bitmap bitmap = textureView.getBitmap();
            stopCameraPreview();  // Stop the camera preview
            sendToApi(bitmap);
        }
    }

    private void stopCameraPreview() {
        if (captureSession != null) {
            captureSession.close();
            captureSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }


    public void sendToApi(Bitmap bitmap) {
        GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-1.5-flash",
                /* apiKey */ "AIzaSyDnLQNo0KIu4psvwi8N-8n4LODWFQU7C54");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder()
                .addText("You are a Pokedex designed to output JSON. Given a description of an object, you should output a JSON object with the following fields: object, species, approximateWeight, approximateHeight, weight, height, hp, attack, defense, speed, type and description. Humans for example would have base health of 100. Another example, if the object is a Golden Retriever, you should output: {object: 'Golden Retriever', species: 'Dog', approximateWeight: '10-20 kg', approximateHeight: '50-60 cm', weight: 15, height:55, hp: 50, attack: 40, defense: 40, speed: 19, type: 'normal',subtype: 'flying'}. Another example for a  {object: 'Magpie', species: 'Bird', approximateWeight: '130 - 270 g', approximateHeight: '37-43 cm', weight: 0.2, height:40, hp: 25, attack: 20, defense: 10, speed: 32, type: 'Flying', subtype: 'electric', description:'It is an electric type Pokémon known for its distinctive appearance and electric powers. Pikachu is small, bipedal, and has yellow fur with black stripes on its back'} If you are given an object that is not a living creature, plant or lifeform, such as a coffee cup, output the same fields but with type: 'Inanimate'. If you are given a description of a person or human, output species: 'Human' and name: 'Person' and type: 'Normal'. If you are not sure what the attributes are for things like height or speed, it is okay to guess. Some examples, plants can have the type as Grass, with the species being Plant. Fish would have the type of Water with the species being Fish. If you are not sure what the sub type of this just guess or give null. Try to keep the types to the options available in pokemon")
                .addImage(bitmap)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        // Create a Handler and Executor
        Handler handler = new Handler(Looper.getMainLooper());
        Executor mainThreadExecutor = handler::post;

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                saveResultToJson(resultText);
                String imagePath = saveImageToCache(bitmap);
                Intent intent = new Intent(CameraActivity.this, PreviewActivity.class);
                intent.putExtra("imagePath", imagePath);
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, mainThreadExecutor);
    }

    private void saveResultToJson(String resultText) {
        Gson gson = new Gson();
        ResultData resultData = new ResultData(resultText);
        String jsonString = gson.toJson(resultData);

        File file = new File(getFilesDir(), "resultAPI.json");

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonString);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String saveImageToCache(Bitmap bitmap) {
        File directory = getCacheDir();
        File imageFile = new File(directory, "captured_image.png");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile.getAbsolutePath();
    }


    private static class ResultData {
        String result;

        ResultData(String result) {
            this.result = result;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        textCameraLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);

        if (textureView.isAvailable()) {
            setupCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCameraPreview();  // Stop the camera when the activity is paused
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

}
