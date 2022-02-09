/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.dokaattila.maven.calorie.calculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author dokaa
 */
public class Controller {

    private static KaloriaSzamlaloFoablak cal;

    /**
     * Statikus main metódus, ami létrehozza a KaloriaSzamlaloFoablakot és
     * láthatóvá teszi
     *
     * @param args
     * @throws KaloriaDAOException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws KaloriaDAOException, SQLException {

        //KaloriaSzamlaloFoablak cal = new KaloriaSzamlaloFoablak();
        Controller.cal = new KaloriaSzamlaloFoablak();
        Controller.cal.setVisible(true);
        Controller.cal.setTitle("Kalória számláló");

    }

    /**
     * Statikus metódus, ami létrehozza az adatbázisból a List-et, melynek
     * minden eleme Meal.
     *
     * @return
     * @throws KaloriaDAOException
     * @throws java.sql.SQLException
     */
    public static List<Meal> getAllMeal() throws KaloriaDAOException, SQLException {
        MealJDBCImpl db = new MealJDBCImpl(getConnection());
        List<Meal> ml = db.findAll();
        return ml;
    }

    /**
     * A statikus metódus létrehozza az adatbázisból a List-et, melynek minden
     * eleme Food.
     *
     * @return
     * @throws KaloriaDAOException
     * @throws java.sql.SQLException
     */
    public static List<Food> getAllFood() throws KaloriaDAOException, SQLException {
        FoodJDBCImpl db = new FoodJDBCImpl(getConnection());
        List<Food> f = db.findAll();
        return f;
    }

    /**
     * Statikus metódus, ami megnyitja a dialógusablakot. Ha dialógusablak
     * getMeal függvénye nem null értékkel tér vissza, hanem egy Optional
     * objektummal, akkor létrehoz egy új Meal objektumot, és beírja azt az
     * adatbázisba. Frissiti a ListModelt is.
     *
     * @throws KaloriaDAOException
     * @throws SQLException
     */
    public static void newMeal() throws KaloriaDAOException, SQLException {
        MealJDBCImpl db = new MealJDBCImpl(getConnection());
        UjModositDialogusAblak dialog = new UjModositDialogusAblak(new java.awt.Frame(), true, null);
        dialog.setVisible(true);
        if (dialog.getMeal(-1) != null) {
            Optional<Meal> uj = dialog.getMeal(-1);
            Meal newMeal = uj.get();
            db.save(newMeal);
            updateList();
            // System.out.println(newMeal.getMealName() +", "+ newMeal.getMealAmount() +", "+ newMeal.getMealID() +", "+ newMeal.getFoodID());
        }

        dialog.setOk(false);
    }

    /**
     * Statikus metódus, ami megnyitja a dialógusablakot. Ha dialógusablak
     * getMeal függvénye nem null értékkel tér vissza, hanem egy Optional
     * objektummal, akkor módosítja az adott Meal objektumot, és beírja azt az
     * adatbázisba. Frissiti a ListModelt is.
     *
     * @param id
     * @param optMeal
     * @throws KaloriaDAOException
     * @throws SQLException
     */
    public static void editMeal(int id, Optional<Meal> optMeal) throws KaloriaDAOException, SQLException {
        MealJDBCImpl db = new MealJDBCImpl(getConnection());
        UjModositDialogusAblak dialog = new UjModositDialogusAblak(new java.awt.Frame(), true, optMeal);
        dialog.setVisible(true);
        if (dialog.getMeal(id) != null) {
            Optional<Meal> uj = dialog.getMeal(id);
            Meal newMeal = uj.get();
            db.save(newMeal);
            updateList();
        }
        dialog.setOk(false);
    }

    /**
     * Törli az adott id-jű Meal objektumot az adatbázisból.
     *
     * @param id
     * @throws KaloriaDAOException
     * @throws SQLException
     */
    public static void deleteMeal(int id) throws KaloriaDAOException, SQLException {

        MealJDBCImpl db = new MealJDBCImpl(getConnection());
        db.deleteMeal(id);
        updateList();
    }

    /**
     * Statikus metódus, ami egy Connection objektummal tér vissza, amiben
     * beállítottuk az adatbázis elérésének paramétereit
     *
     * @return
     * @throws KaloriaDAOException
     */
    public static Connection getConnection() throws KaloriaDAOException {
        Connection conn;
        try {
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaloriaszamlalo?useSSL=false", "root", "1234");
            //conn = DriverManager.getConnection("jdbc:sqlite:c:\\Users\\dokaa\\Documents\\NetBeansProjects\\KaloriaSzamlalo\\Kaloriaszamlalo");
            conn = DriverManager.getConnection("jdbc:sqlite:Kaloriaszamlalo");
//conn = DriverManager.getConnection("jdbc:mysql://65.19.141.67:3306/dokaatti_kaloriaszamlalo?useSSL=false", "dokaatti_user", "355zt5rm0i20");	
        } catch (SQLException ex) {
            throw new KaloriaDAOException("Hiba történt az adatbáziskapcsolatban! Üzenet: " + ex.getMessage());
        }
        return conn;

    }

    /**
     * A főablak listmodeljét frissítő funkció
     *
     * @throws KaloriaDAOException
     * @throws SQLException
     */
    public static void updateList() throws KaloriaDAOException, SQLException {

        Controller.cal.getListModel().clear();
        for (Meal m : Controller.getAllMeal()) {
            Controller.cal.getListModel().addElement(m);
        }
        DecimalFormat f = new DecimalFormat("##.00");
        Controller.cal.getSumCarbLabel().setText(f.format(Controller.updateWindow().get("sumCarb")) + " g");
        Controller.cal.getSumCalLabel().setText(f.format(Controller.updateWindow().get("sumCal")) + " kcal");
        Controller.cal.getSumFatLabel().setText(f.format(Controller.updateWindow().get("sumFat")) + " g");
        Controller.cal.getSumProtLabel().setText(f.format(Controller.updateWindow().get("sumProt")) + " g");

        Controller.cal.getAvgCarbLabel().setText(f.format(Controller.updateWindow().get("avgCarb")) + " g");
        Controller.cal.getAvgCalLabel().setText(f.format(Controller.updateWindow().get("avgCal")) + " kcal");
        Controller.cal.getAvgFatLabel().setText(f.format(Controller.updateWindow().get("avgFat")) + " g");
        Controller.cal.getAvgProtLabel().setText(f.format(Controller.updateWindow().get("avgProt")) + " g");

    }

    public static Map<String, Double> updateWindow() throws KaloriaDAOException, SQLException {
        MealJDBCImpl db = new MealJDBCImpl(getConnection());
        Map<String, Double> m = new HashMap();
        m.put("sumCarb", db.getSumCarb());
        m.put("sumCal", db.getSumCal());
        m.put("sumFat", db.getSumFat());
        m.put("sumProt", db.getSumProt());
        m.put("avgCarb", db.getAvgCarb());
        m.put("avgCal", db.getAvgCal());
        m.put("avgFat", db.getAvgFat());
        m.put("avgProt", db.getAvgProt());
        return m;
    }

    public static void setZero() throws KaloriaDAOException, SQLException {
        MealJDBCImpl db = new MealJDBCImpl(getConnection());
        db.setZero();
        updateList();

    }

}
