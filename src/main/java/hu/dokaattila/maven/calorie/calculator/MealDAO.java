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
public abstract class MealDAO<T> implements DAO {
    
    public abstract void insertMeal(Meal m) throws KaloriaDAOException, SQLException;

    public abstract void updateMeal(Meal m) throws KaloriaDAOException, SQLException;
    
    public abstract void deleteMeal(int id) throws KaloriaDAOException, SQLException;
    
    public abstract double getSumCal() throws KaloriaDAOException;

    public abstract double getSumCarb() throws KaloriaDAOException;

    public abstract double getSumFat() throws KaloriaDAOException;

    public abstract double getSumProt() throws KaloriaDAOException;

    public abstract double getAvgCal() throws KaloriaDAOException;

    public abstract double getAvgCarb() throws KaloriaDAOException;

    public abstract double getAvgFat() throws KaloriaDAOException;

    public abstract double getAvgProt() throws KaloriaDAOException;
    
    public abstract void save(Meal m) throws KaloriaDAOException, SQLException;;

    public abstract Meal makeOne(ResultSet rs) throws SQLException;
    
    public abstract List<T> makeList(ResultSet rs) throws SQLException;
    
    public abstract void setZero() throws SQLException;

}
