package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("패스워드를 초기화한다.")
    @Test
    void passwordTest() {
        // given
        User user = new User();

        // when
        /** as-is
         * user.initPassword(new CorrectFixedPasswordGenerator());
         * (PasswordGenerator Interface를 구현하는 클래스를 만들어서 해도 되지만
         *  해당 인터페이스는 메소드가 하나뿐인 functional interface이기 때문에
         *  굳이 클래스를 새로 안만들고 간편하게 람다식으로 표현이 가능하다.)
         *  **/
        user.initPassword(() -> "12345678");

        // then
        assertThat(user.getPassword()).isNotNull();
    }

    @DisplayName("패스워드가 요구사항에 부합하지 않아 초기화가 되지 않는다.")
    @Test
    void passwordTest2() {
        // given
        User user = new User();

        // when
        user.initPassword(() -> "12");

        // then
        assertThat(user.getPassword()).isNull();
    }
}