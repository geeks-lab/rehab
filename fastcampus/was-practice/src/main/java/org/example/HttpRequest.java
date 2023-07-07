package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestLine requestLine;


    //프로토콜의 첫번째 라인이 requestLine이기 때문에 첫번쨰 라인을 집어넣어주면 requestLine객체가 우리가 세팅한 형태로 만들어줄것이다.
    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br.readLine());
    }

    public boolean isGetRequest() {
        // RequestLine이 값을 가지고 있기 때문에 거기에 물어보기
        return requestLine.isGetRequest();
    }


    public boolean matchPath(String requestPath) {
        return requestLine.matchPath(requestPath);
    }

    public QueryStrings getQueryStrings() {
        return requestLine.getQueryStrings();
    }
}
