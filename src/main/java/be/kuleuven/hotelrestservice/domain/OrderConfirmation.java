package be.kuleuven.hotelrestservice.domain;

import java.util.List;

public class OrderConfirmation {

    protected String id;
    protected String address;
    protected List<MealOrder> meals;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MealOrder> getMeals() {
        return meals;
    }

    public void setMeals(List<MealOrder> meals) {
        this.meals = meals;
    }

}

