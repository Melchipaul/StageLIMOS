package DS4Alpha.Operator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class Operator {

    //******************************************************************************************************************
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
    //******************************************************************************************************************

    private int                   operatorTypeID        = 0;

    private DoubleUnaryOperator   unaryOperator         = null;
    private DoubleBinaryOperator  binaryOperator        = null;
    private DoubleNaryOperator    naryOperator          = null;
    private String                operatorString;

    //******************************************************************************************************************
    //  Special attributes for unary operators
    //  touchingDate is used to denote whether this is the first unary operator
    //  unaryParameter is used to store the parameter in the unary function, e.g. f = ax, a is the parameter
    //******************************************************************************************************************

    private double                unaryParameter        = 0;


    //******************************************************************************************************************
    // Constructors
    // Based on different received elements, the corresponding constructor method is called.
    // It is noteworthy that only unary, binary, n-ary and functional composition operator is necessary to store, and
    // they will be used in the transformation from infix to postfix, postfix to alphaTree. Then after alphaTree,
    // functional composition does not exist any more.
    //******************************************************************************************************************

    public Operator (DoubleUnaryOperator unaryOperator, int operatorTypeID, String operatorString) {
        this.unaryOperator  = unaryOperator;
        this.operatorTypeID = operatorTypeID;
        this.operatorString = operatorString;
    }

    public Operator (DoubleUnaryOperator unaryOperator, int operatorTypeID, double unaryParameter, String operatorString) {
        this.unaryOperator  = unaryOperator;
        this.operatorTypeID = operatorTypeID;
        this.unaryParameter = unaryParameter;
        this.operatorString = operatorString;
    }

    public Operator (DoubleBinaryOperator binaryOperator, int operatorTypeID, String operatorString) {
        this.binaryOperator = binaryOperator;
        this.operatorTypeID = operatorTypeID;
        this.operatorString = operatorString;
    }
    public Operator (DoubleNaryOperator naryOperator, int operatorTypeID, String operatorString) {
        this.naryOperator   = naryOperator;
        this.operatorTypeID = operatorTypeID;
        this.operatorString = operatorString;
    }
    public Operator (int operatorTypeID, String operatorString) {
        this.operatorTypeID = operatorTypeID;
        this.operatorString = operatorString;
    }


    // get the function in this operator
    public DoubleUnaryOperator getUnaryOperator() {
        return this.unaryOperator;
    }
    public DoubleBinaryOperator getBinaryOperator() {
        return  this.binaryOperator;
    }
    public DoubleNaryOperator getNaryOperator() {
        return this.naryOperator;
    }

    // get the operator type
    public int getOperatorTypeId() {
        return this.operatorTypeID ;
    }

    //get the operator parameter
    public double getUnaryParameter() {
        return this.unaryParameter;
    }

    // get the string of this operator
    public String getOperatorString() {
        return this.operatorString;
    }
}


//****************************** deprecated codes **********************************************************************
//
//    private LinearMap             linearOperator        =null;
//    private LogOperator           logOperator           =null;
//    private PowerOperator         powerOperator         =null;
//    private ExpOperator           expOperator           =null;
//
//*****************************************************************
//    Operator (LinearMap linearOperator, double operatorTypeID){
//        this.linearOperator  = linearOperator;
//        this.operatorTypeID  = operatorTypeID;
//    }
//    Operator (LogOperator logOperator, double operatorTypeID){
//        this.logOperator     = logOperator;
//        this.operatorTypeID  = operatorTypeID;
//    }
//    Operator (PowerOperator powerOperator, double operatorTypeID){
//        this.powerOperator   = powerOperator;
//        this.operatorTypeID  = operatorTypeID;
//    }
//    Operator (ExpOperator expOperator, double operatorTypeID) {
//        this.expOperator     = expOperator;
//        this.operatorTypeID  = operatorTypeID;
//    }
//*****************************************************************






//    public Operator (String parenthesisOrComposition, double operatorTypeID){
//        if  (parenthesisOrComposition.trim().equals("(")) {
////            this.leftParenthesis = "(";
//            this.operatorTypeID = operatorTypeID;
////            this.operatorType = 5;
//        }
//        else if(parenthesisOrComposition.trim().equals(")")){
////            this.rightParenthesis = ")";
//            this.operatorTypeID = operatorTypeID;
////            this.operatorType =6;
//        }
//        else if(parenthesisOrComposition.trim().equals("o")){
////            this.functionalComposition = "o";
//            this.operatorTypeID = operatorTypeID;
////            this.operatorType = 4;
//        }
//        // if none of the above option is matched, then catch an exception
//    }
//*********************************************************************************************************************