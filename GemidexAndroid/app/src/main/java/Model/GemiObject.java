package Model;

public class GemiObject {
    private String typeId;
    private  String gemiName;
    private String description;
    private String weight;
    private String heigh;
    private String category;
    private String speed;

    public GemiObject(String speed, String category, String heigh, String weight, String description, String gemiName, String typeId) {
        this.speed = speed;
        this.category = category;
        this.heigh = heigh;
        this.weight = weight;
        this.description = description;
        this.gemiName = gemiName;
        this.typeId = typeId;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHeigh() {
        return heigh;
    }

    public void setHeigh(String heigh) {
        this.heigh = heigh;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGemiName() {
        return gemiName;
    }

    public void setGemiName(String gemiName) {
        this.gemiName = gemiName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}

