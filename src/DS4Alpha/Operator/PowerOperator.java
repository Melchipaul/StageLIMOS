package DS4Alpha.Operator;

import java.util.function.DoubleUnaryOperator;

public class PowerOperator {

    private DoubleUnaryOperator unaryOperator;
    private double exponent;

    PowerOperator (DoubleUnaryOperator unaryOperator, double exponent) {
        this.unaryOperator = unaryOperator;
        this.exponent = exponent;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return this.unaryOperator;
    }

    public double getExponent() {
        return this.exponent;
    }

}
