package org.example.mvc;


import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.controller.RequestMethod;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping{
    private final Object[] basePackage;

    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    // 생성자
    public AnnotationHandlerMapping(Object... basePackage){
        this.basePackage = basePackage;
    }

    // Reflection을 이용해서 Map(handlers)초기화
    public void initialize(){
        Reflections reflections = new Reflections(basePackage);

        // basePackage 밑에 controller annotation 이 붙은 클래스 타입의 객체를 다 받아오기 -> 여기선 HomeController 뿐이지만 여러개라 가정.
        Set<Class<?>> clazzesWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);

        // 그 안에 method에 RequestMapping이 붙어져 있는 애들 다 가져오기
        clazzesWithControllerAnnotation.forEach(clazz ->
                        Arrays.stream(clazz.getDeclaredMethods()).forEach(declaredMethod -> {
                            RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(RequestMapping.class);

                            // @RequestMapping(value = "/", method = RequestMethod.GET)
                            // 위 값을 다 가져와서 HandlerKey를 만들어준다.
                            Arrays.stream(getRequestMethods(requestMapping))
                                    .forEach(requestMethod -> handlers.put(
                                            new HandlerKey(requestMethod, requestMapping.value()), new AnnotationHandler(clazz, declaredMethod)
                                    ));
                        })
                );
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        // GET 인지 POST 인지 리턴
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
