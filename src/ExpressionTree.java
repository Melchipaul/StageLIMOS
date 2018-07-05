/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Stack;

/**
 *
 * @author nduwayo
 */
public class ExpressionTree {
    static boolean isBinaryOperator(String mytSring) {
        if (mytSring.equals("+") || mytSring.equals("-") || mytSring.equals("*") || mytSring.equals("/")) {
            return true;
        }
        return false;
    }
    Node treeConstruction(String[] postFix){
        Stack<Node> nodeStack = new Stack<Node>();
        
        Node tempNode = new Node();
        for(int i = 0; i < postFix.length;i++){
            String tempOperator = postFix[i];
            
           if(Traitements.isAlpha(tempOperator) || Traitements.isNary(tempOperator)){
               tempNode.value = tempOperator;
               nodeStack.push(tempNode);
           }
           if(isBinaryOperator(tempOperator)){
               tempNode.value = tempOperator;
               tempNode.right = nodeStack.pop();
               tempNode.left = nodeStack.pop();
               nodeStack.push(tempNode);
           }
           
           if(tempOperator.equals("∘")){
               Node downNode;
               Node leftMost;
               downNode = nodeStack.pop();
               tempNode = nodeStack.pop();
               leftMost = tempNode;
               while(leftMost != null){
                   leftMost = leftMost.left;
               }
               leftMost.left = downNode;
               nodeStack.push(tempNode);
           }
        }
        tempNode = nodeStack.pop();
        return tempNode;
    }
}
