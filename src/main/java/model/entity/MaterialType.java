package model.entity;

/**
 * Created by adam on 26/02/2017.
 */
public class MaterialType implements Entity {
    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
