package fr.uge.rmi.common;

import java.util.ArrayList;

public class Bike {

    private final long bikeId;
    private final long ownerId;
    private String ownerName = null;
    private String bikeName = null;
    private String description = null;
    private long userId = -1; // -1 if rentable -2 if bike is unRentable if >= 0 bike is already rent by someone
    private final float locationPrice;
    private final float resalePrice;
    private boolean used = false;
    private final ArrayList<Long> waiters = new ArrayList<>();
    private final ArrayList<Integer> grades = new ArrayList<>();


    public Bike(long ownerId1, String ownerName, long bikeId, String name, float LocationPrice, float resalePrice, String desc) {

        if(ownerId1 < 0) throw new IllegalArgumentException("id must be > 0");
        this.ownerId = ownerId1;
        this.ownerName = ownerName;
        if(LocationPrice < 0) throw new IllegalArgumentException("locationPrice must be > 0");
        this.locationPrice = LocationPrice;
        if(ownerId1 < 0) throw new IllegalArgumentException("ownerId1 must be > 0");
        this.bikeId = bikeId;
        this.bikeName = name;
        this.description = desc;
        this.resalePrice = resalePrice;
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

    public void setNextUserId() {this.userId = waiters.remove(0);}

    public boolean isRented() {
        return userId >= 0;
    }
    public boolean isInCart() {
        return userId == -2;
    }

    public void unRent() {if (userId == -1) userId = -2;}

    public void reRent() {userId = -1;}

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
        if(userId > -2) this.waiters.add(userId);
    }


    public long getBikeId() {
        return bikeId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getUserId() {
        return userId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getBikeName() {
        return bikeName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public ArrayList<Long> getWaiters() {
        return waiters;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }
    public float getAvgGrade() {
        float total = 0;
        if(grades.size() > 0) {
            for (int grade:  grades) {
                total += grade;
            }
            return total / grades.size();
        }
        return total;
    }

    public float getResalePrice() {
        return resalePrice;
    }

    @Override
    public String toString() {
       return new StringBuilder("\n{")
                .append("bike_id: " + bikeId + "\n")
                .append("owner_id: " + ownerId + "\n")
                .append("owner_name: " + ownerName + "\n")
                .append("name: " + bikeName + "\n")
                .append("desc: "+ description + "\n")
                .append("user_id: " + userId + "\n")
                .append("location_price: " + locationPrice + "\n")
                .append("resale_price: " + resalePrice + "\n")
                .append("used: " + used + "\n")
                .append("waiters: " + waiters + "\n")
                .append("grades: " + grades + "\n")
                .append("avg_grade: " + this.getAvgGrade() + "\n")
                .append("}")
                .toString();
    }
}
