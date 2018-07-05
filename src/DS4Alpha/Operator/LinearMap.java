package DS4Alpha.Operator;

import java.util.function.DoubleUnaryOperator;

public class LinearMap {

    private DoubleUnaryOperator unaryOperator;
    private double slope;

    LinearMap (DoubleUnaryOperator unaryOperator, double slope){
        this.unaryOperator = unaryOperator;
        this.slope = slope;
    }

    public double getSlope() {
        return this.slope;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return this.unaryOperator;
    }
}
