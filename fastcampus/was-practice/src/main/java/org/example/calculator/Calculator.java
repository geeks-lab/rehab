package org.example.calculator;

import org.example.calculator.*;

import java.util.List;

public class Calculator {
    // 인터페이스를 만들고 해당 인터페이스를 구현하는 구현체 네개를 만들었다.
    //각각의 구현체들을 상위 인터페이스인 NewArithmeticOperator를 통해서 변수에 받는다.
    private static final List<NewArithmeticOperator> arithmeticOperators = List.of(new AdditionOperator(), new SubtractionOperator(), new MultiplcationOperator(), new DivisionOperator());
    public static int calculate(PositiveNumber operand1, String operator, PositiveNumber operand2) {
        return arithmeticOperators.stream()
                // 이 operator에 해당하는 구현체를 필터링해서
                .filter(arithmeticOperators -> arithmeticOperators.supports(operator))
                // calculate 작업을 구현체에게 위임했고 결과값이 int이기 때문에 map을 해줬다.
                .map(arithmeticOperators -> arithmeticOperators.calculate(operand1, operand2))
                // 첫번째 값을 받는다
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다"));
    }
}
