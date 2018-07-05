/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DS4Alpha.AlphaExpressionTree.AlphaExpressionTree;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nduwayo
 */
public class Traitements {

    private ArrayList<ArrayList<Double>> drag1 = new ArrayList<ArrayList<Double>>();
    private String drag = "";
    private String jsonOutput = "";
    private String jsonTerminate = "";
    private String jsonPartialAgregation = "";

    public Traitements(HttpServletRequest request) {
        String madrag = request.getParameter("drag");
        setDrag(madrag);
        setJsonOutput();
        setJsonTerminate();
        setJsonPartialAgregation();
        
    }

    /**
     * @return the drag
     */
    public String getDrag() {
        return this.drag;
    }
    public String getJsonOutput(){
        return this.jsonOutput;
    }
    
    public ArrayList<ArrayList<Double>> getDrag1() {
        return this.drag1;
    }

    /**
     * @param drag the drag to set
     */
    public void setDrag(String drag) {
        String[] myString = drag.split(" ");
        ArrayList<String> myArray = infixToPostfix(myString);
        for(int i = 0; i < myArray.size(); i++){
        this.drag += myArray.get(i);
        }
        
        ParseString myDrag = new ParseString();
        
        myDrag.parsing(myString);
        
        this.drag1 = myDrag.getMyAlphaPars();

    }
    
    static int predecence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "∘":
                return 3;
            default:
                return -1;

        }

    }

    static boolean isOperator(String mytSring) {
        if (mytSring.equals("+") || mytSring.equals("-") || mytSring.equals("*") || mytSring.equals("/") || mytSring.equals("∘")) {
            return true;
        }
        return false;
    }

    static boolean isNary(String myString){
        if(myString.equals("∑") || myString.equals("∏") ){
            return true;
        }
        return false;
    }
    static boolean isParanthesis(String myString){
       if(myString.equals("(") ||myString.equals(")") ){
           return true;
       } 
       return false;
    }
    static boolean isAlpha(String myString) {
        boolean isAlpha = false;
        if (!isNary(myString) && !isOperator(myString) && !isParanthesis(myString)) {
            isAlpha = true;
        }
        return isAlpha;
    }

    static ArrayList<String> infixToPostfix(String[] infix) {
        ArrayList<String> postFix = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();
        for (int i = 0; i < infix.length; i++) {
            String tempOperator = infix[i];
            if (isAlpha(tempOperator) || isNary(tempOperator)) {
                postFix.add(tempOperator);
            }
            if (isOperator(tempOperator)) {
                while (!operatorStack.empty() && predecence(tempOperator) <= predecence(operatorStack.peek())) {
                    postFix.add(operatorStack.pop());
                }
                operatorStack.push(tempOperator);
            }
            if (tempOperator.equals("(")) {
                operatorStack.push(tempOperator);
            }
            if (tempOperator.equals(")")) {
                while (!operatorStack.empty() && !operatorStack.peek().equals("(")) {
                    postFix.add(operatorStack.pop());
                }
                operatorStack.pop();
            }

        }
        while (!operatorStack.empty()) {
            postFix.add(operatorStack.pop());
        }
        return postFix;
    }

    /**
     * @param jsonOutput the jsonOutput to set
     */
    public void setJsonOutput() {
        Alpha myAlpha = new Alpha(getDrag1());
        this.jsonOutput = myAlpha.getAlphaTreeJson();
    }

    /**
     * @return the jsonTerminate
     */
    public String getJsonTerminate() {
        return this.jsonTerminate;
    }

    /**
     * @param jsonTerminate the jsonTerminate to set
     */
    public void setJsonTerminate() {
        Alpha myAlpha = new Alpha(getDrag1());
        this.jsonTerminate = myAlpha.getWFAofAlpha().getT().printAlphaTreeInJson();
    }

    /**
     * @return the jsonPartialAgregation
     */
    public String getJsonPartialAgregation() {
        return this.jsonPartialAgregation;
    }

    /**
     * @param jsonPartialAgregation the jsonPartialAgregation to set
     */
    public void setJsonPartialAgregation() {
        
      
        Alpha myAlpha = new Alpha(getDrag1());
        for (int i = 0; i < myAlpha.getWFAofAlpha().getPartialAggregation().size(); i++){
            if(i < myAlpha.getWFAofAlpha().getPartialAggregation().size()-1){
           this.jsonPartialAgregation += AlphaExpressionTree.printTree(myAlpha.getWFAofAlpha().getPartialAggregation().get(i))+",";
            }else{
                this.jsonPartialAgregation += AlphaExpressionTree.printTree(myAlpha.getWFAofAlpha().getPartialAggregation().get(i));
            }
        }
        
        
    }

}
