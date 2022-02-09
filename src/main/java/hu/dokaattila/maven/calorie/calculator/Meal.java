/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.dokaattila.maven.calorie.calculator;

/**
 *
 * @author dokaa
 */
public class Meal {

    private String mealName;
    private double mealAmount;
    private int mealID;
    private int foodID;

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public Meal(String mealName, double mealAmount, int mealID, int foodID) {
        this.mealName = mealName;
        this.mealAmount = mealAmount;
        this.mealID = mealID;
        this.foodID = foodID;
    }

    Meal() {
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getMealAmount() {
        return mealAmount;
    }

    public void setMealAmount(double mealAmount) {
        this.mealAmount = mealAmount;
    }

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    @Override
    public String toString() {
        return this.mealName +"("+ this.mealAmount +"g)";
    }

}
