package DS4Alpha.AlphaExpressionTree;

import DS4Alpha.Operator.Operator;
import java.util.ArrayList;
import java.util.Stack;

public class AlphaExpressionTree {

    private Node root;
    private ArrayList<Operator> infixExpression   ;
    private ArrayList<Operator> postfixExpression ;
    private WellFormedAggregation WFA             ;

    public AlphaExpressionTree( ArrayList<Operator> infixExpression ) {
        this.infixExpression = infixExpression;
    }
    public AlphaExpressionTree( Node root ) {
        this.root = root;
    }


    public AlphaExpressionTree getAlphaExpressionTree ( ) {

        generateAlphaExpression();

        return this;
    }

    public WellFormedAggregation getWFA() {

        if (this.WFA == null) decomposeAlphaExpressionTree();

        return WFA;
    }


    private void generateAlphaExpression () {
        //      Generate the postfix expression
        if (this.postfixExpression == null)
            this.postfixExpression = infix2Postfix( this.infixExpression );

        //      Generate the alphaTree
        if (this.root == null)
            this.root = postfix2AlphaExpressionTree( this.postfixExpression ).root;

    }

    private void decomposeAlphaExpressionTree ( ) {

        this.WFA = decompose( new AlphaExpressionTree(this.root));

    }

    // several methods are used to construct the tree and decompose it to generate well-formed aggregation.
    // 1. infix2Postfix
    // 2. postfix2AlphaTree
    // 3. decompose

    // 1. infix2postfix method, this implementation is based on the algorithm infix2Postfix
    private ArrayList<Operator> infix2Postfix( ArrayList<Operator> infixExpression ){
        Stack<Operator>     operatorStack     = new Stack<>();
        Operator            tempOperator;
        int                 operatorTypeID;
        ArrayList<Operator> postfixExpression = new ArrayList<>();

        for( int i=0; i<infixExpression.size(); i++){
            tempOperator    = infixExpression.get(i);
            operatorTypeID  = tempOperator.getOperatorTypeId();

            // If the current operator is unaryOperator or naryOperator;
            if( operatorTypeID >=1 && operatorTypeID <= 50 || operatorTypeID >= 61 && operatorTypeID <= 70 ){
                postfixExpression.add( tempOperator );
            }

            //If the current operator is a binary operator or functional composition
            if ( operatorTypeID >=51 && operatorTypeID <=60 || operatorTypeID == 71){
                while ( operatorStack.empty()!= true && precedence( tempOperator ) < precedence( operatorStack.peek() ) ){
                    postfixExpression.add(operatorStack.pop());
                }
                operatorStack.push( tempOperator );
            }

            //If the current operator is a leftParenthesis
            if ( operatorTypeID == 72 ){
                operatorStack.push( tempOperator );
            }

            //If the current operator is a rightParenthesis
            if ( operatorTypeID == 73 ){
                while(operatorStack.empty()!= true && operatorStack.peek().getOperatorTypeId() != 72){
                    postfixExpression.add(operatorStack.pop());
                }
                operatorStack.pop();
            }
        }

        while( operatorStack.empty() != true ){
            postfixExpression.add( operatorStack.pop( ) );
        }

        return postfixExpression;

    }
    private int precedence(Operator operator){
        double operatorType = operator.getOperatorTypeId();
        if (operatorType == 51 || operatorType == 52)
            return 1;
        if (operatorType == 53 || operatorType == 54)
            return 2;
        if (operatorType == 71)
            return 3;
        return -1;
    }


    //2. postfix2AlphaExpressionTree, this implementation is based on the algorithm postfix2AlphaExpressionTree.
    private AlphaExpressionTree postfix2AlphaExpressionTree( ArrayList<Operator> postfixExpression ){
        Stack<Node> nodeStack = new Stack<>();
        AlphaExpressionTree alphaTree;
        Operator tempOperator;
        int operatorTypeID;

        for(int i = 0; i < postfixExpression.size(); i++){
            tempOperator = postfixExpression.get(i);
            operatorTypeID = tempOperator.getOperatorTypeId();
            Node tempNode;
            // If the current operator is a unary or nary operator
            if (operatorTypeID >=1 && operatorTypeID <= 50 || operatorTypeID >= 61 && operatorTypeID <= 70 ){
                tempNode = new Node( tempOperator );
                nodeStack.push( tempNode );
            }

            // If the current operator is a binary operator
            if ( operatorTypeID >=51 && operatorTypeID <=60 ){
                tempNode = new Node(tempOperator);
                tempNode.setRightChild(nodeStack.pop());
                tempNode.setLeftChild(nodeStack.pop());
                nodeStack.push(tempNode);
            }

            // If the current operator is a functional composition
            if ( operatorTypeID == 71 ){
                Node downNode;
                Node leftMost;
                downNode = nodeStack.pop();
                tempNode = nodeStack.pop();
                leftMost = tempNode;
                while( leftMost.getLeftChild() != null ) {
                    leftMost = leftMost.getLeftChild();
                }
                leftMost.setLeftChild( downNode );
                nodeStack.push(tempNode);
            }
        }

        alphaTree = new AlphaExpressionTree( nodeStack.pop() );

        return alphaTree;
    }


//  decompose alphaExpression tree, this implementation is based on the algorithm decompose alpha tree.
    private WellFormedAggregation decompose( AlphaExpressionTree alphaTree ){
        Stack<Node> nodeStack1                = new Stack<>();
        Stack<Node> nodeStack2                = new Stack<>();
        WellFormedAggregation WFA;
        ArrayList<Node> F                     = new ArrayList<>();
        ArrayList<Operator> OPLUS             = new ArrayList<Operator>();
        AlphaExpressionTree T;
        ArrayList<Node> partialAggregation    = new ArrayList<Node>();
        Node tempNode1;
        Node tempNode2;


//      *****************************************************
//      If ( alphaTree.getRoot().getOperator().equals(null) )
//            return error;
//      CATCH AN EXCEPTION HERE
//      *****************************************************


//      If the root of the alpha expression tree contains nary operator, then this alpha tree is simply returned
        if ( alphaTree.getRoot().getOperator().getOperatorTypeId() >= 61 && alphaTree.getRoot().getOperator().getOperatorTypeId() <= 70 ) {
            partialAggregation.add(alphaTree.getRoot());
            F.add( alphaTree.getRoot().getLeftChild() );
            OPLUS.add( alphaTree.getRoot().getOperator() );
            T = new AlphaExpressionTree( new Node( new Operator( (x) -> x, 10,"x" )) );
            WFA = new WellFormedAggregation( F, OPLUS, T, partialAggregation );
            return WFA;
        }

        nodeStack1.push( alphaTree.getRoot() );
        T =  alphaTree.getCopy();
        nodeStack2.push( T.getRoot() );

        // A pre-order traversal is executed simultaneously on stack1 and stack2 in order to find out the nary operator.
        while ( nodeStack1.empty()!= true ) {
            tempNode1 = nodeStack1.pop();
            tempNode2 = nodeStack2.pop();
            // if the current node contains a nary operator, then its operator is added into OPLUS
            if ( tempNode1.getOperator().getOperatorTypeId()>=61 && tempNode1.getOperator().getOperatorTypeId() <= 70 ) {
                partialAggregation.add( tempNode1 );
                F.add( tempNode1.getLeftChild() );
                OPLUS.add( tempNode1.getOperator() );
                tempNode2.setLeftChild( null );
                continue;
            }

            // if the current node has a non-empty right and left children, then
            if (tempNode1.getRightChild() != null) {
                nodeStack1.push(tempNode1.getRightChild());
                nodeStack2.push(tempNode2.getRightChild());
            }
            if (tempNode1.getLeftChild() != null) {
                nodeStack1.push(tempNode1.getLeftChild());
                nodeStack2.push(tempNode2.getLeftChild());
            }
        }

        WFA = new WellFormedAggregation( F,OPLUS,T,partialAggregation );

        return WFA;
    }

//  This method is used to get a copy of the current alphaTree
    public AlphaExpressionTree getCopy( ) {

        Node root = this.getRoot();
        AlphaExpressionTree copiedTree = new AlphaExpressionTree( getCopy( root ) );

        return copiedTree;
    }
    private Node getCopy ( Node rootOfThisTree ) {

        Node rootOfNewTree = new Node( rootOfThisTree.getOperator() );

        if ( rootOfThisTree.getLeftChild() != null ) {
            rootOfNewTree.setLeftChild( getCopy( rootOfThisTree.getLeftChild() ) );
        }
        if ( rootOfThisTree.getRightChild() != null ) {
            rootOfNewTree.setRightChild( getCopy( rootOfThisTree.getRightChild() ) );
        }

        return rootOfNewTree;
    }


//  Print out this alphaExpressionTree in a string potentially used for a jason library
    public String printAlphaTreeInJson ( ) {

        if (this.root == null)
            generateAlphaExpression();

        return printTree(this.getRoot());
    }
    public static String printTree ( Node roofOfThisTree ) {

        StringBuffer printedTree = new StringBuffer(" text: { name: \" " + roofOfThisTree.getOperator().getOperatorString() + " \" } ");

//      If the current node has leftChild or rightChild, then put a children label in the jason output string.
//      It is noteworthy that if the root contains one child, then it must be leftChild.
        if (  roofOfThisTree.getLeftChild() != null ||  roofOfThisTree.getRightChild() != null ) {
            printedTree.append(", children: [ " );
            if (  roofOfThisTree.getLeftChild() != null ) {
                printedTree.append( " { " + printTree( roofOfThisTree.getLeftChild( ) ) + " } " );
            }
            if (  roofOfThisTree.getRightChild() != null ) {
                printedTree.append( ", { " + printTree( roofOfThisTree.getRightChild( ) ) + " } " );
            }
            printedTree.append(" ] ");
        }

        return printedTree.toString();
    }



//    public void setRoot(Node root) {
//        this.root = root;
//    }

    public Node getRoot() {
        return this.root;
    }
}


