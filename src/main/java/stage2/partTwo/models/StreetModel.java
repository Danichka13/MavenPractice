package stage2.partTwo.models;

public class StreetModel extends BaseModel {
    private String name;
    private int idLoc;
    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getIdLocality() {
        return idLoc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdLocality(int idLoc) {
        this.idLoc = idLoc;
    }

    private StreetModel() {
    }

    public StreetModel(long id, String name, int idLoc, String type) {
        super(id);
        this.name = name;
        this.idLoc = idLoc;
        this.type = type;
    }
}
