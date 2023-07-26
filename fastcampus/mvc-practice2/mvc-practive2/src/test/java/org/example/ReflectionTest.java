package org.example;

import org.example.annotation.Controller;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @Controller 애노테이션이 설정돼 있는 모든 클래스를 찾아서 출력한다.
 * */
public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        // org.example 하위에 해당하는 모든 클래스에 리플랙션을 사용할 것이다
        Reflections reflections = new Reflections("org.example");

        // controller annotation이 있는 대상들을 찾아서 beans라는 해쉬셋에 넣기
        Set<Class<?>> beans = new HashSet<>();
        beans.addAll(reflections.getTypesAnnotatedWith(Controller.class));

        logger.debug("beans : [{}]",beans);

        /**
         * 위 부분 리팩토링
         * */

    }
}
