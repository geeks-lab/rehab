package org.example.mvc.controller;

import org.example.mvc.model.User;
import org.example.mvc.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserCreateController implements Controller {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRepository.save(new User(request.getParameter("userId"), request.getParameter("name")));
        return "redirect:/users";
        /**
         * 생성이 되면 리다이렉트문에서 클라이언트에게 "/users라는 경로에 다시한번 요청해줘" 라고 응답을 보냄
         *  -> 웹브라우저가 응답을 받고 /users 경로로 GET 요청을 보낸다.
         *  -> GET 요청이 들어오면 DispatcherServlet이 받아서 GET 요청에 /users 경로에 해당하는 것이 있는지 HandlerMapping을 통해 확인하고
         *     핸들러매핑은 핸들러키에 해당되는 컨트롤러를 실행시키게 된다.
         *
         * */
    }
}
