package fr.uge.rmi.common;

import java.util.ArrayList;

public class Bike {
    private final long bikeId;
    private final long ownerId;
    private long userId = -1; // -1 if the bike is in the shop
    private final float locationPrice;
    private boolean used = false;
    private final ArrayList<Long> waiters = new ArrayList<>();
    private final ArrayList<Integer> grades = new ArrayList<>();

    public Bike(long ownerId1, float LocationPrice, long bikeId) {

        if(ownerId1 < 0) throw new IllegalArgumentException("id must be > 0");
        this.ownerId = ownerId1;
        if(LocationPrice < 0) throw new IllegalArgumentException("locationPrice must be > 0");
        this.locationPrice = LocationPrice;
        if(ownerId1 < 0) throw new IllegalArgumentException("ownerId1 must be > 0");
        this.bikeId = bikeId;
    }

    private void use() {
        used = true;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        if (!used) use();
    }

    public int UnsetUserId(long userId) {
        if (userId != this.userId) throw new IllegalArgumentException("You must have the bike to turn it in.");
        this.userId = -1;
        return this.waiters.size();
    }

    public void setNextUserId() {
        this.userId = waiters.remove(0);
    }

    public boolean isRented() {
        return userId != -1;
    }

    public void addGrade(int grade){
        if (grade < 1 || grade > 5) throw new IllegalArgumentException("Grade must be between 1 and 5.");
        grades.add(grade);
    }

    public float getLocationPrice() {
        return locationPrice;
    }

    public double getGrade() {
        return grades.stream().mapToDouble(a->a).average().orElse(0);
    }

    public long getId() { return bikeId;}

    public void add(long userId) {
        this.waiters.add(userId);
    }


}
