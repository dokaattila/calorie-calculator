/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.dokaattila.maven.calorie.calculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dokaa
 * @param <T>
 */
public class MealJDBCImpl<T> extends MealDAO {

    private Connection conn;
    private PreparedStatement findAll;
    private PreparedStatement findById;
    private PreparedStatement findByName;
    private PreparedStatement insertMeal;
    private PreparedStatement updateMeal;
    private PreparedStatement deleteMeal;
    private PreparedStatement sumCal;
    private PreparedStatement sumCarb;
    private PreparedStatement sumFat;
    private PreparedStatement sumProt;
    private PreparedStatement avgCal;
    private PreparedStatement avgCarb;
    private PreparedStatement avgFat;
    private PreparedStatement avgProt;
    private PreparedStatement setZero;
    private LocalDate today = LocalDate.now();

    public MealJDBCImpl(Connection conn) throws SQLException {
        this.conn = conn;
        this.findAll = conn.prepareStatement("SELECT * FROM userfood WHERE uf_date ='" + this.today + "'");
        this.findById = conn.prepareStatement("SELECT * FROM userfood WHERE uf_id = ?");
        this.insertMeal = conn.prepareStatement("INSERT INTO userfood (fl_id,uf_name,uf_amount,uf_date) VALUES (?,?,?,?)");
        this.updateMeal = conn.prepareStatement("UPDATE userfood SET uf_name = ?, fl_id = ?, uf_amount = ? WHERE uf_id = ?");
        this.deleteMeal = conn.prepareStatement("DELETE FROM userfood WHERE uf_id = ?");
        this.sumCarb = conn.prepareStatement("SELECT SUM(food_carb/100*uf_amount) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id WHERE uf_date = '" + this.today + "'");
        this.sumCal = conn.prepareStatement("SELECT SUM(food_calories/100*uf_amount) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id WHERE uf_date = '" + this.today + "'");
        this.sumFat = conn.prepareStatement("SELECT SUM(food_fat/100*uf_amount) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id WHERE uf_date = '" + this.today + "'");
        this.sumProt = conn.prepareStatement("SELECT SUM(food_protein/100*uf_amount) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id WHERE uf_date = '" + this.today + "'");
        this.avgCarb = conn.prepareStatement("SELECT (SUM(food_carb/100*uf_amount) / COUNT(DISTINCT uf_date)) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id");
        this.avgCal = conn.prepareStatement("SELECT (SUM(food_calories/100*uf_amount) / COUNT(DISTINCT uf_date)) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id");
        this.avgFat = conn.prepareStatement("SELECT (SUM(food_fat/100*uf_amount) / COUNT(DISTINCT uf_date)) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id");
        this.avgProt = conn.prepareStatement("SELECT (SUM(food_protein/100*uf_amount) / COUNT(DISTINCT uf_date)) AS result FROM userfood LEFT JOIN foodlist ON fl_id = foodlist.id");
        this.setZero = conn.prepareStatement("DELETE FROM userfood WHERE uf_date < '"+ this.today+"'");
    }

    @Override
    public List<T> findAll() throws KaloriaDAOException {
        List<T> ret;
        
        try (ResultSet all = this.findAll.executeQuery()) {
            ret = makeList(all);
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
        return ret;
    }

    @Override
    public void close() {
        try {
            this.findAll.close();
        } catch (SQLException ex) {
        }

    }

    @Override
    public void insertMeal(Meal m) throws KaloriaDAOException, SQLException {
        this.insertMeal.setInt(1, m.getFoodID());
        this.insertMeal.setString(2, m.getMealName());
        this.insertMeal.setDouble(3, m.getMealAmount());
        //this.insertMeal.setDate(4, Date.valueOf(today));//MYSQL
        this.insertMeal.setString(4, this.today.toString());//SQLite
        this.insertMeal.executeUpdate();
    }

    @Override
    public void save(Meal m) throws KaloriaDAOException, SQLException {
        if (m.getMealID() == -1) {
            insertMeal(m);
        } else {
            updateMeal(m);
        }
    }

    @Override
    public void updateMeal(Meal m) throws KaloriaDAOException, SQLException {
        this.updateMeal.setString(1, m.getMealName());
        this.updateMeal.setInt(2, m.getFoodID());
        this.updateMeal.setDouble(3, m.getMealAmount());
        this.updateMeal.setInt(4, m.getMealID());
        this.updateMeal.executeUpdate();
    }

    @Override
    public void deleteMeal(int id) throws KaloriaDAOException, SQLException {
        this.deleteMeal.setInt(1, id);
        this.deleteMeal.executeUpdate();
    }

    @Override
    public List<T> makeList(ResultSet rs) throws SQLException {
        List<T> ret = new ArrayList<>();
        while (rs.next()) {
            ret.add((T) makeOne(rs));
        }
        return ret;
    }

    @Override
    public void setZero() throws SQLException {
        this.setZero.executeUpdate();
    }
    
    @Override
    public Meal makeOne(ResultSet rs) throws SQLException {
        Meal m = new Meal();
        m.setMealID(rs.getInt("uf_id"));
        m.setMealName(rs.getString("uf_name"));
        m.setMealAmount(rs.getDouble("uf_amount"));
        return m;
    }

    @Override
    public List findById() throws KaloriaDAOException {
        return null;
    }

    @Override
    public List findByName() throws KaloriaDAOException {
        return null;
    }

    @Override
    public double getSumCarb() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.sumCarb.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }

    }

    @Override
    public double getSumCal() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.sumCal.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

    @Override
    public double getSumFat() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.sumFat.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

    @Override
    public double getSumProt() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.sumProt.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

    @Override
    public double getAvgCal() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.avgCal.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

    @Override
    public double getAvgCarb() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.avgCarb.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

    @Override
    public double getAvgFat() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.avgFat.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

    @Override
    public double getAvgProt() throws KaloriaDAOException {
        double ertek = 0;
        try (ResultSet rs = this.avgProt.executeQuery()) {
            while (rs.next()) {
                ertek = rs.getDouble("result");
            }
            return ertek;
        } catch (SQLException ex) {
            throw new KaloriaDAOException(ex);
        }
    }

}
