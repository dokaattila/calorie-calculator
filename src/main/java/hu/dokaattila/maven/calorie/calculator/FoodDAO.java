/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.dokaattila.maven.calorie.calculator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dokaa
 * @param <T>
 */
public abstract class FoodDAO <T> implements DAO{

    @Override
    public List<T> findAll() throws KaloriaDAOException{
        return null;
    }

    @Override
    public List<T> findById() {
        return null;
    }

    @Override
    public List<T> findByName() {
        return null;
    }
    
    public abstract Food makeOne(ResultSet rs) throws SQLException;
    
    public abstract List<T> makeList(ResultSet rs) throws SQLException;
    
    
    
}
