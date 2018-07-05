package DS4Alpha.AlphaExpressionTree;

import DS4Alpha.Operator.Operator;

public class Node {

    private Operator operator;
    private Node     leftChild     ;
    private Node     rightChild    ;

    Node(Operator operator) {
        this.operator   = operator;
        this.leftChild  = null;
        this.rightChild = null;
    }

    protected void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    protected void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Operator getOperator() {
        return this.operator;
    }
}
