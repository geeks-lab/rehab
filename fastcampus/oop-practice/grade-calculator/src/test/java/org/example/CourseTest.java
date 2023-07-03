package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class CourseTest {


    @DisplayName("과목을 생성한다.")
    @Test
    void CreateTest() {
        assertThatCode(() -> new Course("OOP",3,"A+"))
                .doesNotThrowAnyException();
    }
}
