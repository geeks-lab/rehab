package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.HomeController;

import java.util.HashMap;
import java.util.Map;

// 경로 -> 컨트롤러 매핑
public class RequestMappingHandlerMapping {
    private Map<String, Controller> mappings = new HashMap<>();

    // 어떠한 경로도 설정되어 있지 않다면 홈컨트롤러로
    void init(){
        mappings.put("/", new HomeController());
    }

    // 경로에 맞는 컨트롤러 찾아주기
    public Controller findHandler(String uriPath){
        return mappings.get(uriPath);
    }

}
