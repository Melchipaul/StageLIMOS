package DS4Alpha.Operator;

import java.util.function.DoubleUnaryOperator;

public class LogOperator {

    private DoubleUnaryOperator unaryOperator;
    private double base;

    LogOperator (DoubleUnaryOperator unaryOperator, double base) {
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
