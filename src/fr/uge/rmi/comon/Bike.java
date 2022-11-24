package fr.uge.rmi.comon;

import java.util.ArrayList;

public class Bike {
    private final long bikeId;
    private final long ownerId;
    private long userId = -1; // -1 if the bike is in the shop
    private final float LocationPrice;
    private boolean used = false;
    private final ArrayList<Integer> grades = new ArrayList<>();

    public Bike(long ownerId1, float LocationPrice, long bikeId) {

        this.ownerId = ownerId1;
        this.LocationPrice = LocationPrice;
        this.bikeId = bikeId;
    }

    private void use() {
        used = true;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        if (!used) use();
    }

    public void UnsetUserId(long userId) {
        if (userId != this.userId) throw new IllegalArgumentException("You must have the bike to turn it in.");
        this.userId = -1;
    }

    public boolean isRented() {
        return userId != -1;
    }

    public void addGrade(int grade){
        if (grade < 1 || grade > 5) throw new IllegalArgumentException("Grade must be between 1 and 5.");
        grades.add(grade);
    }

    public float getPrice() {
        return price;
    }

    public double getGrade() {
        return grades.stream().mapToDouble(a->a).average().orElse(0);
    }

    public long getId() { return bikeId;}
}
