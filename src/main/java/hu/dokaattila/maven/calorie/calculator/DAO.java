/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.dokaattila.maven.calorie.calculator;

import java.util.List;

/**
 *
 * @author dokaa
 * @param <T>
 */
public interface DAO <T>{
    
    public List<T> findAll() throws KaloriaDAOException;
    public List<T> findById() throws KaloriaDAOException;
    public List<T> findByName() throws KaloriaDAOException;
    public void close() throws KaloriaDAOException;
   
    
}
