package org.example.mvc.controller;

import org.example.mvc.HandlerAdapter;
import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {

    // 해당 어댑터는 컨트롤러를 구현한 구현채여야지만 사용할 수 있는 어댑터
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof Controller);
    }

    // 컨트롤러를 구현한 구현채라면 핸들러를 컨트롤러로 타입캐스팅해주고 viewName을 객체로 감싸서 리턴
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String viewName = ((Controller) handler).handleRequest(request, response);
        return new ModelAndView(viewName);
    }

}
