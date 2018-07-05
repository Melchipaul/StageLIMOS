package DS4Alpha.Operator;

import java.util.function.DoubleBinaryOperator;

public class DoubleNaryOperator {

    private DoubleBinaryOperator OPLUS;

    public DoubleNaryOperator (DoubleBinaryOperator aBinaryOperator){
        this.OPLUS =aBinaryOperator;
    }

    public DoubleBinaryOperator getOPLUS() {
        return this.OPLUS;
    }

}
