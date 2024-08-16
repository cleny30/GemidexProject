package IRepository;

public interface UploadCloudinaryCallback {
    void onSuccess(String url);
    void onError(String errorMessage);
}
