import DS4Alpha.AlphaExpressionTree.AlphaExpressionTree;
import DS4Alpha.AlphaExpressionTree.WellFormedAggregation;
import DS4Alpha.Operator.DoubleNaryOperator;
import DS4Alpha.Operator.Operator;

import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//******************************************************************************************************************
//
//    lazy evaluation
//
//
//  Operator type ID is used in the algorithm of transforming infix expression to postfix expression
//  OperatorType table is illustrated in the following.
//
//  operatorType                ID range
//  unaryOperator               1-50
//  binaryOperator              51-60
//  NaryOperator                61-70
//  FunctionalComposition       71
//  LeftParenthesis             72
//  RightParenthesis            73
//
//
//  unaryOperator               ID
//  linearOperator              1
//  logOperator                 2
//  powOperator                 3
//  expoOperator                4
//  acos                        5
//  asin                        6
//  atan                        7
//  cos                         8
//  sin                         9
//  identify                    10
//  constant                    11
//
//  binaryOperatorType          ID
//  +                           51
//  -                           52
//  *                           53
//  /                           54
//
//  naryOperatorType            ID
//  sigma sum                   61
//  pi    prod                  62
//  ********************************************************************************************************************

public class Alpha {

    private ArrayList<Operator>  infixAlphaWithFC         ;
    private LinkedList<Operator> infixAlpha               = new LinkedList<>();
//  The following two attributes are used to store the well-formed aggregation and alpha tree of this alpha.

    private WellFormedAggregation WFAofAlpha          = null;
    private AlphaExpressionTree   alphaTree           = null;
    private String                alphaTreeJson       = null;


//  ********************************************************************************************************************
//  Two constructor methods. One is for only using the library, another one is for using the graphical interface.
//  ********************************************************************************************************************
//  The constructor for the library.
    public Alpha() {
    }

//  The constructor for graphical interface, receiving a two dimensional array of double.
    public Alpha( ArrayList<ArrayList<Double>> inputArray ) {
        int typeID;
        for ( int i = 0;  i < inputArray.size(); i++ ) {
            typeID = inputArray.get(i).get(0).intValue();
            switch ( typeID ) {
//                unary operator
                case 1 : linearMap          ( inputArray.get(i).get(1) );               break;
                case 2 : generateLog        ( inputArray.get(i).get(1).intValue() );    break;
                case 3 : pow                ( inputArray.get(i).get(1) );               break;
                case 4 : exp                ( inputArray.get(i).get(1) );               break;
                case 5 : acos               ();                                         break;
                case 6 : asin               ();                                         break;
                case 7 : atan               ();                                         break;
                case 8 : cos                ();                                         break;
                case 9 : sin                ();                                         break;
                case 10: idenity            ();                                         break;
                case 11: constant           ( inputArray.get(i).get(1));                break;

//                binary operator
                case 51: add                ();                                         break;
                case 52: sub                ();                                         break;
                case 53: times              ();                                         break;
                case 54: divide             ();                                         break;

//                n-ary operator
                case 61: summation          ();                                         break;
                case 62: product            ();                                         break;

//                functional composition
                case 71: funcComp           ();                                         break;

//                left parenthesis
                case 72: leftParenthesis    ();                                         break;
                case 73: rightParenthesis   ();                                         break;

            }
        }

    }
    private void generateLog ( int baseOfLog ) {
        switch ( baseOfLog ) {
            case 1    : log2 ();   break;
            case 2    : log10();   break;
            case 3    : ln   ();   break;
        }
    }




//  **********************************************************************************************************************
//  unary operators
//
//  In each function, firstly addIntoInfixAlpha is called to add an operator into the infix expression of alpha, then
//  updateFormula is called to update the output string of the mathematical formula.
//  **********************************************************************************************************************

    public Alpha linearMap (double slope){
        addIntoInfixAlpha(
                new Operator(
                        (x) ->  x * slope,  1, slope, slope + "x"
                )
        );

        return this;
    }

    public Alpha ln(){
        addIntoInfixAlpha(
                new Operator(
                        (x) ->  Math.log(x), 2, Math.E,"ln(x)"
                )
        );

        return this;
    }

    public Alpha log2(){
        addIntoInfixAlpha(
                new Operator(
                        (x) ->  Math.log(x)/Math.log(2.0), 2,2,"log2(x)"
                )
        );

        return this;
    }

    public Alpha log10() {
        addIntoInfixAlpha(
                new Operator(
                        (x) ->  Math.log10(x),2,10,"log10(x)"
                )
        );

        return this;
    }

    public Alpha pow (double exponent){
        addIntoInfixAlpha(
                new Operator(
                        (x) -> Math.pow(x, exponent),3, exponent,"x^"+ exponent
                )
        );

        return this;
    }

    public Alpha exp (double base){
        addIntoInfixAlpha(
                new Operator(
                        (x) -> Math.pow(base, x), 4,base,base + "^x"
                )
        );

        return this;
    }


    public Alpha acos( ){
        addIntoInfixAlpha(
                new Operator(
                        (x) -> Math.acos(x),5,"acos(x)"
                )
        );

        return this;
    }
    public Alpha asin( ){
        addIntoInfixAlpha(
                new Operator(
                        (x) -> Math.asin(x),6,"asin(x)"
                )
        );

        return this;
    }
    public Alpha atan( ){
        addIntoInfixAlpha(
                new Operator(
                        (x) -> Math.atan(x),7,"atan(x)"
                )
        );

        return this;
    }
    public Alpha cos( ) {
        addIntoInfixAlpha(
                new Operator(
                        (x) ->  Math.cos(x),8,"cos(x)"
                )
        );

        return this;
    }
    public Alpha sin( ) {
        addIntoInfixAlpha(
                new Operator(
                        (x) ->  Math.sin(x),9,"sin(x)"
                )
        );

        return this;
    }
    public Alpha idenity( ) {
        addIntoInfixAlpha(
                new Operator(
                        (x) -> x,10,"x"
                )
        );

        return this;
    }
    public Alpha constant( double constant ) {
        addIntoInfixAlpha(
                new Operator(
                        (x) -> constant, 11, ""+ constant
                )
        );

        return this;
    }


//  ********************************************************************************************************************
//  binary operator
//  ********************************************************************************************************************

    public Alpha add ( ){
        addIntoInfixAlpha(
                new Operator(
                        (x,y) -> x+y, 51," + "
                )
        );

        return this;
    }
    public Alpha sub ( ){
        addIntoInfixAlpha(
                new Operator(
                        (x,y) -> x-y, 52," - "
                )
        );

        return this;
    }
    public Alpha times ( ){
        addIntoInfixAlpha(
                new Operator(
                        (x,y) -> x*y, 53," * "
                )
        );

        return this;
    }
    public Alpha divide ( ){
        addIntoInfixAlpha(
                new Operator(
                        (x,y) -> x/y,54," / "
                )
        );

        return this;
    }


//  ********************************************************************************************************************
//   N-ary operator
//  ********************************************************************************************************************

    public Alpha summation ( ){
        addIntoInfixAlpha(
                new Operator(
                        new DoubleNaryOperator( (x, y) -> x + y ),61,"\u03A3"
                )
        );

        return  this;
    }

    public Alpha product ( ){
        addIntoInfixAlpha(
                new Operator(
                        new DoubleNaryOperator( (x,y) -> x * y ),62,"\u03A0"
                )
        );

        return  this;
    }

//  ********************************************************************************************************************
//  Functional composition
//  ********************************************************************************************************************

    public Alpha funcComp ( ){
        addIntoInfixAlpha(
                new Operator(
                        71,"\u2218"
                )
        );

        return this;
    }

//  ********************************************************************************************************************
//  Left and right parenthesis
//  ********************************************************************************************************************

    public Alpha leftParenthesis ( ) {
        addIntoInfixAlpha(
                new Operator(
                        72,"("
                )
        );

        return this;
    }

    public Alpha rightParenthesis ( ){
        addIntoInfixAlpha(
                new Operator(
                        73," )"
                )
        );

        return this;
    }



//  ********************************************************************************************************************
//  Put operators into the infix expression
//  ********************************************************************************************************************

    private void addIntoInfixAlpha ( Operator operator ) {

        infixAlpha.add(operator);

    }

//  ********************************************************************************************************************
//   Methods to add functional composition and left parenthesis between nary operator and unary or parenthesis
//  ********************************************************************************************************************

    private void insertFunctionalComposition () {

        LinkedList<Operator> tempLinkedList = new LinkedList<>(this.infixAlpha);

        int currentOperatorType, nextOperatorType;

        for (int i = 0; i < tempLinkedList.size(); i ++) {

            currentOperatorType  = tempLinkedList.get(i).getOperatorTypeId();

//            if the current operator is a n-ary operator
            if ( currentOperatorType >=61 && currentOperatorType <= 70 ){

                nextOperatorType = tempLinkedList.get(i+1).getOperatorTypeId();

//                if the next operator is a unary operator
                if ( nextOperatorType >= 1 && nextOperatorType <= 50) {
                    i += 1;
                    Operator functionalComposition = new Operator(71,"\u2218" );
                    tempLinkedList.add(i,functionalComposition);
                    i += 1;
                    Operator leftParenthesis = new Operator(72,"(" );
                    tempLinkedList.add(i,leftParenthesis);
                    i += 2;
//                    insert right parenthesis

                    while ( i < tempLinkedList.size() ) {
                        //  Locating binary operation
                        if (tempLinkedList.get(i).getOperatorTypeId() >= 51 && tempLinkedList.get(i).getOperatorTypeId()<= 54) {
//                            System.out.println("locating a binary operator");
                            if ( tempLinkedList.get(i+1).getOperatorTypeId() == 72 || (tempLinkedList.get(i+1).getOperatorTypeId() >= 61 && tempLinkedList.get(i+1).getOperatorTypeId() <= 62 )){
                                tempLinkedList.add(i, new Operator(73,")"));
//                                System.out.println("add a right parenthesis");
                                break;
                            }
                        }

                        i += 1;
                    }

//                  if none right pattern is located, then the index should point to the last element of the linkedList, in this case add the right parenthesis to the end.
                    if ( i == tempLinkedList.size()  )
                        tempLinkedList.add(i, new Operator(73, ")"));

                }

//                if the next operator is a left parenthesis
                if ( nextOperatorType == 72){
                    i += 1;
                    Operator functionalComposition = new Operator(71,"\u2218" );
                    tempLinkedList.add( i, functionalComposition );
                    i += 2;
//                  counting meaningful right parenthesis
                    int meaningfulRightParenthesis = 0;
                    while ( tempLinkedList.get(i).getOperatorTypeId() != 73 || meaningfulRightParenthesis > 0 ){
//                        if an embedded left parenthesis is meet;
                        if ( tempLinkedList.get(i).getOperatorTypeId() == 72)
                            meaningfulRightParenthesis += 1;
//                        if an embedded right parenthesis is meet;
                        if ( meaningfulRightParenthesis > 0 && tempLinkedList.get(i).getOperatorTypeId() == 73)
                            meaningfulRightParenthesis -= 1;
                        i += 1;
                    }
                }

            }


            //  if a leftParenthesis or a unary operator is meet, at the same time the previous
//            if ( ( currentOperatorType == 72 || currentOperatorType >= 1 && currentOperatorType <= 50  ) && (previousOperatorType >= 61 && previousOperatorType <= 70) ) {
//                Operator functionalComposition = new Operator(71,"\u2218" );
//                tempLinkedList.add(i,functionalComposition);
//            }
        }
        this.infixAlphaWithFC = new ArrayList<>(tempLinkedList);
    }

//  ********************************************************************************************************************
//  Methods to generate alphaTree and WellFormedAggregation
//  ********************************************************************************************************************


   // Get the infixExpression of the current alpha tree
    public LinkedList<Operator> getInfixAlpha() {
        return this.infixAlpha;
    }

    //  Generate the alpha expression tree and then store it in the alphaTree attribute of this alpha object
    public AlphaExpressionTree getAlphaTree() {

        generateAlphaExpressionTree();

        return this.alphaTree;

    }
    private void generateAlphaExpressionTree ( ) {

        if (this.infixAlphaWithFC == null) {
            insertFunctionalComposition();
        }

        if ( this.alphaTree == null  )
             this.alphaTree = new AlphaExpressionTree( this.infixAlphaWithFC ).getAlphaExpressionTree() ;

    }

    //  Decompose the alpha expression tree, which will firstly call the method generateAlphaExpressionTree
    public WellFormedAggregation getWFAofAlpha() {

        decomposeAlpha();

        return this.WFAofAlpha;

    }
    private void decomposeAlpha ( ) {

        generateAlphaExpressionTree();

        if ( this.WFAofAlpha == null )
             this.WFAofAlpha = this.alphaTree.getWFA();

    }

   // Print the current tree in a json format potentially used for a json library
    public String getAlphaTreeJson() {

        if ( this.alphaTreeJson == null)
            generateJsonOutput();

        return this.alphaTreeJson;

    }
    private void generateJsonOutput ( ) {

        generateAlphaExpressionTree();

        if ( this.alphaTreeJson == null)
            this.alphaTreeJson = this.alphaTree.printAlphaTreeInJson();

    }

    //  **************print math formula******************

    private void printExpression ( List<Operator> expression ) {

        StringBuffer formula =  new StringBuffer();

        for(int i = 0; i < expression.size(); i++)
            formula.append(expression.get(i).getOperatorString());

        System.out.println(formula.toString());

    }


    public void printInfixFormula( ){
        printExpression(this.infixAlpha);
    }

    public void printInfixFormulaWithFC( ) {
        if (this.infixAlphaWithFC == null)
            insertFunctionalComposition();
        printExpression(this.infixAlphaWithFC);
    }

}

































//***************** eager evaluation***************************
//****unary operator
//    public F linearMap (double slope){
//
//        if (!combineFlag){
//            this.output = this.output * slope;
//            updateFormula(slope + "(" + this.mathFormula.toString(), true);
//        } else{
//            this.temp = this.temp * slope;
//            updateFormula(slope + "(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F ln(){
//        if (!combineFlag){
//            this.output =  Math.log(this.output);
//            updateFormula("ln(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.log(this.temp);
//            updateFormula("ln(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F log2(){
//        if (!combineFlag){
//            this.output =  Math.log(this.output)/Math.log(2.0);
//            updateFormula("log2(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.log(this.temp)/Math.log(2.0);
//            updateFormula("log2(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F log10() {
//        if (!combineFlag){
//            this.output = Math.log10(this.output);
//            updateFormula("log10(" + this.mathFormula.toString(), true);
//        } else {
//            this.output = Math.log10(this.temp);
//            updateFormula("log10(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F pow (double exponent){
//        if (!combineFlag){
//            this.output =  Math.pow(this.output, exponent);
//            updateFormula("(" + this.mathFormula.toString() + ")" + "^" + exponent, false);
//        } else {
//            this.temp =  Math.pow(this.temp, exponent);
//            updateFormula("(" + this.tempMathFormula.toString() + ")" + "^" + exponent, false);
//        }
//
//        return this;
//    }
//
//    public F exp (double base){
//        if (!combineFlag){
//            this.output =  Math.pow(base, this.output);
//            updateFormula("(" + base + ")" + "^" + this.mathFormula.toString(), false);
//        } else {
//            this.temp = Math.pow(base, this.temp);
//            updateFormula("(" + base + ")" + "^" + this.tempMathFormula.toString(), false);
//        }
//
//        return this;
//    }
//
//
//    public F acos( ){
//        if (!combineFlag){
//            this.output =  Math.acos(this.output);
//            updateFormula("acos(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.acos((this.temp));
//            updateFormula("acos(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F asin( ){
//        if (!combineFlag){
//            this.output =  Math.asin(this.output);
//            updateFormula("asin(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.asin(this.temp);
//            updateFormula("asin(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F atan( ){
//        if (!combineFlag){
//            this.output =  Math.atan(this.output);
//            updateFormula("atan(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.atan(this.temp);
//            updateFormula("atan(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F cos( ) {
//        if (!combineFlag){
//            this.output =  Math.cos(this.output);
//            updateFormula("cos(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.cos(this.temp);
//            updateFormula("cos(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F sin( ) {
//        if (!combineFlag){
//            this.output =  Math.sin(this.output);
//            updateFormula("sin(" + this.mathFormula.toString(), true);
//        } else {
//            this.temp = Math.sin(this.temp);
//            updateFormula("sin(" + this.tempMathFormula.toString(), true);
//        }
//
//        return this;
//    }
//
//    public F abs( ){
//        if (!combineFlag){
//            this.output =  Math.abs(this.output);
//            updateFormula("|" + this.mathFormula.toString() + "|", false);
//        } else {
//            this.temp = Math.abs(this.temp);
//            updateFormula("|" + this.tempMathFormula.toString() + "|", false);
//        }
//        return this;
//    }



//    eager evaluation for changing combine flag
//    private void updateFormula (String newUnary, boolean updateAtBegining){
//        if (!combineFlag) {
//            this.mathFormula.setLength(0);
//            if (updateAtBegining) {
//                this.mathFormula.append(newUnary + ")");
//            } else {
//                this.mathFormula.append(newUnary);
//            }
//        } else {
//            this.tempMathFormula.setLength(0);
//            if (updateAtBegining) {
//                this.tempMathFormula.append(newUnary + ")");
//            } else {
//                this.tempMathFormula.append(newUnary);
//            }
//        }
//    }


//****binary operator**************
//    public F add (F f ){
//        this.output += f.temp;
//
//        combineTwoStringFormula(this,f," + ");
//
//        changeFlag();
//        return this;
//    }
//
//    public F sub (F f ){
//        this.output -= f.temp;
//
//        combineTwoStringFormula(this, f," - ");
//
//        changeFlag();
//        return this;
//    }
//
//    public F times (F f ){
//        this.output *= f.temp;
//
//        combineTwoStringFormula(this, f, " * ");
//
//        changeFlag();
//        return this;
//    }
//
//    public F divide (F f ){
//        this.output /= f.temp;
//
//        combineTwoStringFormula(this, f, " / ");
//
//        changeFlag();
//        return this;
//    }
//
//    private void combineTwoStringFormula (F firstOperand, F secondOperand, String binaryOperation){
//        String firstString  = firstOperand.mathFormula.toString();
//        String secondString = secondOperand.tempMathFormula.toString();
//
//        firstOperand.mathFormula.setLength(0);
//        secondOperand.tempMathFormula.setLength(0);
//
//        firstOperand.mathFormula.append(firstString + binaryOperation + secondString);
//    }
//
//    private void changeFlag (){
//        this.combineFlag = false;
//        this.temp = 0.0;
//    }
//

