/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nduwayo
 */
public class AlphaExpression {
    String dateCreation;
    String expression;
    
    public AlphaExpression(String myString){
        String format = "dd/MM/yy H:mm:ss"; 
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
        java.util.Date date = new java.util.Date();
        this.dateCreation = formater.format(date);
        this.expression = myString;
    }
    
}
