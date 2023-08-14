package org.example.di;

import org.example.controller.UserController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private final Set<Class<?>> preInstantiatedClazz;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
    }
    // 인자로 넘어온 클래스타입 객체를 키로 전달해서 인스턴스를 반환한다.
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
}
