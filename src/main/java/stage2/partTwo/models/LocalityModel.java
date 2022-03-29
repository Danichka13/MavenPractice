package stage2.partTwo.models;

import java.util.Date;

public class LocalityModel extends BaseModel {
    private int square;
    private String name;
    private String type;
    private Date date;
    private double population;

    public int getSquare() {
        return square;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getDateOfFoundation() {
        return date;
    }

    public double getPopulation() {
        return population;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateOfFoundation(Date date) {
        this.date = date;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public LocalityModel() {
    }

    public LocalityModel(long id, String name, String type, int square, double population, Date date) {
        super(id);
        this.name = name;
        this.type = type;
        this.square = square;
        this.population = population;
        this.date = date;
    }
}