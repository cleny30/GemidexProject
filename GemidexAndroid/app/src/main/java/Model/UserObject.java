package Model;

public class UserObject {
    private int userId;
    private String email;
    private String fullName;
    private String googleId;

    public UserObject(int userId,  String email, String fullName, String googleId) {
        this.googleId = googleId;
        this.fullName = fullName;
        this.email = email;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
