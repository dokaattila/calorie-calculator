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
public class KaloriaDAOException extends Exception{

    public KaloriaDAOException() {
    }

    public KaloriaDAOException(String string) {
        super(string);
    }

    public KaloriaDAOException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public KaloriaDAOException(Throwable thrwbl) {
        super(thrwbl);
    }
    
    
    
}
