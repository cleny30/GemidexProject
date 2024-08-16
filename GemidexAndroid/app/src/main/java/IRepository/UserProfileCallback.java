package IRepository;

import Model.UserObject;

public interface UserProfileCallback {
    void onResult(boolean isRegistered);
    void onError(Throwable t);
}
