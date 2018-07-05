package DS4Alpha.AlphaExpressionTree;

import DS4Alpha.Operator.Operator;
import java.util.ArrayList;

public class WellFormedAggregation {

    private ArrayList<Node>                     F;
    private ArrayList<Operator>                 OPLUS;
    private AlphaExpressionTree                 T;
    private ArrayList<Node>                     partialAggregation;

    public WellFormedAggregation(ArrayList<Node> f, ArrayList<Operator> OPLUS, AlphaExpressionTree t, ArrayList<Node> partialAggregation) {
        this.F = f;
        this.OPLUS = OPLUS;
        this.T = t;
        this.partialAggregation = partialAggregation;
    }

    public ArrayList<Node> getF() {
        return F;
    }

    public ArrayList<Operator> getOPLUS() {
        return OPLUS;
    }

    public AlphaExpressionTree getT() {
        return T;
    }

    public ArrayList<Node> getPartialAggregation() {
        return partialAggregation;
    }
}
