package DS4Alpha.Operator;

import java.util.function.DoubleUnaryOperator;

public class ExpOperator {

    private DoubleUnaryOperator unaryOperator;
    private double base;

    ExpOperator(DoubleUnaryOperator unaryOperator, double base) {
        this.unaryOperator = unaryOperator;
        this.base = base;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return unaryOperator;
    }

    public double getBase() {
        return base;
    }
}
