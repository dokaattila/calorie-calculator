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
public class Food {

    private int foodID;
    private String foodName;
    private double foodFat;
    private double foodCarb;
    private double foodProtein;
    private double foodcalories;

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(double foodFat) {
        this.foodFat = foodFat;
    }

    public double getFoodCarb() {
        return foodCarb;
    }

    public void setFoodCarb(double foodCarb) {
        this.foodCarb = foodCarb;
    }

    public double getFoodProtein() {
        return foodProtein;
    }

    public void setFoodProtein(double foodProtein) {
        this.foodProtein = foodProtein;
    }


    public void setFoodcalories(double foodcalories) {
        this.foodcalories = foodcalories;
    }

    @Override
    public String toString() {
        return this.foodName;
    }

}
