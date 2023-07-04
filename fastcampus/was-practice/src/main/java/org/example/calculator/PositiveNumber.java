package org.example.calculator;


//양수 validation을 위한 value object 생성
public class PositiveNumber {
    private final int value;

    public PositiveNumber(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        //value값이 음수가 맞다면 예외를 만든다.
        if (isNegativeNumber(value)) {
            throw new IllegalArgumentException("0또는 음수를 전달할 수 없습니다.");
        }
    }

    private boolean isNegativeNumber(int value) {
        return value <= 0;
    }

    public int toInt() {
        return value;
    }
}
