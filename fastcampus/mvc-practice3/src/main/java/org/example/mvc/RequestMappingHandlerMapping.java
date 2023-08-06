package org.example.mvc;

import org.example.mvc.controller.*;

import java.util.HashMap;
import java.util.Map;

// 경로 -> 컨트롤러 매핑
public class RequestMappingHandlerMapping {
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    // 어떠한 경로도 설정되어 있지 않다면 홈컨트롤러로
    void init(){
        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserListController());
        //mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
    }

    // 경로에 맞는 컨트롤러 찾아주기
    public Controller findHandler(HandlerKey handlerKey){
        return mappings.get(handlerKey);
    }

}
