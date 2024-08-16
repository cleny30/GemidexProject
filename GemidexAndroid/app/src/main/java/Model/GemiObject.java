package Model;

public class GemiObject {
    private  String googleId;
    private String typeName;
    private String subTypeName;
    private String gemiName;
    private String description;
    private String weight;
    private String heigh;
    private String category;
    private String speed;
    private String img;

    public GemiObject(String googleId, String typeName, String subTypeName, String gemiName, String description, String weight, String heigh, String category, String speed, String img) {
        this.googleId = googleId;
        this.typeName = typeName;
        this.subTypeName = subTypeName;
        this.gemiName = gemiName;
        this.description = description;
        this.weight = weight;
        this.heigh = heigh;
        this.category = category;
        this.speed = speed;
        this.img = img;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public String getGemiName() {
        return gemiName;
    }

    public void setGemiName(String gemiName) {
        this.gemiName = gemiName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeigh() {
        return heigh;
    }

    public void setHeigh(String heigh) {
        this.heigh = heigh;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

