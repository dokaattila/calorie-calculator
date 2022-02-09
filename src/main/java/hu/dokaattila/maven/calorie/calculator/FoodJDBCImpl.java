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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dokaa
 * @param <T>
 */
public class FoodJDBCImpl<T> extends FoodDAO {

    private Connection conn;
    private PreparedStatement findAll;
    private PreparedStatement findById;
    private PreparedStatement findByName;

    public FoodJDBCImpl(Connection conn) throws SQLException {
        this.conn = conn;
        this.findAll = conn.prepareStatement("SELECT * FROM foodlist ORDER BY food_name");
        this.findById = conn.prepareStatement("SELECT * FROM foodlist WHERE id = ?");

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
    public List<T> makeList(ResultSet rs) throws SQLException {
        List<T> ret = new ArrayList<>();
        while (rs.next()) {
            ret.add((T) makeOne(rs));
        }
        return ret;
    }

    @Override
    public Food makeOne(ResultSet rs) throws SQLException {
        Food f = new Food();
        f.setFoodID(rs.getInt("id"));
        f.setFoodName(rs.getString("food_name"));
        f.setFoodFat(rs.getDouble("food_fat"));
        f.setFoodProtein(rs.getDouble("food_protein"));
        f.setFoodcalories(rs.getDouble("food_calories"));
        f.setFoodCarb(rs.getDouble("food_carb"));
        return f;
    }

    @Override
    public void close() throws KaloriaDAOException {
        try {
            this.findAll.close();
        } catch (SQLException ignored) {

        }

    }
}
