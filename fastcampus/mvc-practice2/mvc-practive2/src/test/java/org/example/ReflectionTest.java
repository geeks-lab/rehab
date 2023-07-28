package org.example;

import org.example.annotation.Controller;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reflection
 * • 힙 영역에 로드돼 있는 클래스 타입의 객체를 통해 필드/메소드/생성자를 접근 제어자와 상관 없이 사용할 수 있도록 지원하는 API
 * • 컴파일 시점이 아닌 런타임 시점에 동적으로 특정 클래스의 정보를 추출해낼 수 있는 프로그래밍 기법
 *
 * 실습 : @Controller 애노테이션이 설정돼 있는 모든 클래스를 찾아서 출력한다.
 * */
public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class));
        logger.debug("beans : [{}]",beans);
    }

    private Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        // org.example 하위에 해당하는 모든 클래스에 리플랙션을 사용할 것이다
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        return beans;
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        // 리플렉션을 이용해 클래스에 선언된 필드들 모두 데려와보기
        logger.debug("all fields declared in the User class: [{}]", Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toList()));
        // 모든 생성자
        logger.debug("all constructors in the User class: [{}]", Arrays.stream(clazz.getDeclaredConstructors())
                .collect(Collectors.toList()));
        // 모든 메소드
        logger.debug("all methods declared in User class: [{}]", Arrays.stream(clazz.getDeclaredMethods())
                .collect(Collectors.toList()));
    }

    @Test
    void load() throws ClassNotFoundException {
        // heap 영역에 load 되어있는 class 객체를 가져오는 방법
        // 1
        Class<User> clazz = User.class;

        // 2
        User user = new User("churn82", "jiyoung");
        Class<? extends User> clazz2 = user.getClass();

        // 3
        Class<?> clazz3 = Class.forName("org.example.model.User");

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
    }
}
