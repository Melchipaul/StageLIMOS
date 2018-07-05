import DS4Alpha.AlphaExpressionTree.AlphaExpressionTree;


public class DS4Alpha {

    public static void main(String[] args)
    {
//      Initialize an alpha object with the library constructor.
        Alpha alpha = new Alpha().summation().leftParenthesis().pow(2).add().pow(3).rightParenthesis().divide().summation().log2();

//      The following example is for using the graphical constructor api to initialize an alpha object.
//      Alpha alpha = new Alpha( receivedTwoDimensionalArray );

//      Print the infix expression from the user's input.
        alpha.printInfixFormula();
        System.out.println();

//      Print the infix expression with adding the functional composition between nary operator and unary operator.
        alpha.printInfixFormulaWithFC();
        System.out.println();

//      Print this alpha tree.
        System.out.println( "The alpha expression tree in JSON string: " );
        System.out.println( alpha.getAlphaTreeJson() );
        System.out.println();
//      Print the details of this alpha tree.
        System.out.println( "The root: "                                       + alpha.getAlphaTree().getRoot().getOperator().getOperatorString());
        System.out.println( "The left child of root: "                         + alpha.getAlphaTree().getRoot().getLeftChild().getOperator().getOperatorString());
        System.out.println( "The left child of left child of root: "           + alpha.getAlphaTree().getRoot().getLeftChild().getLeftChild().getOperator().getOperatorString());
        System.out.println( "The right child of root: "                        + alpha.getAlphaTree().getRoot().getRightChild().getOperator().getOperatorString());
        System.out.println( "The left child of the right child of the root: "  + alpha.getAlphaTree().getRoot().getRightChild().getLeftChild().getOperator().getOperatorString());
        System.out.println( "The first operator in F: "                        + alpha.getWFAofAlpha().getF().get(0).getOperator().getOperatorString());
        System.out.println( "The second operator in F: "                       + alpha.getWFAofAlpha().getF().get(1).getOperator().getOperatorString());
        System.out.println( "The first operator in OPLUS: "                    + alpha.getWFAofAlpha().getOPLUS().get(0).getOperatorString());
        System.out.println( "The second operator in OPLUS: "                   + alpha.getWFAofAlpha().getOPLUS().get(1).getOperatorString());
        System.out.println( "The root of T: "                                  + alpha.getWFAofAlpha().getT().getRoot().getOperator().getOperatorString());
        System.out.println( "The root of the first partial aggregation: "      + alpha.getWFAofAlpha().getPartialAggregation().get(0).getOperator().getOperatorString());
        System.out.println();

//      Print the terminating function of the alpha object in jason string.
        System.out.println("The terminating function in JSON string: ");
        System.out.println(alpha.getWFAofAlpha().getT().printAlphaTreeInJson());
        System.out.println();

//      Print the partial aggregation of the alpha object in json string.
        System.out.println("The partial aggregation in JSON string: ");
        for (int i = 0; i < alpha.getWFAofAlpha().getPartialAggregation().size(); i++){
            System.out.println( AlphaExpressionTree.printTree(alpha.getWFAofAlpha().getPartialAggregation().get(i)) );
        }



    }
}
