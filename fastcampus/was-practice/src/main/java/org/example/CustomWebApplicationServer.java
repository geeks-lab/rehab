package org.example;

import org.example.calculator.Calculator;
import org.example.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {
    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket((port))) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for a client.");

            //serverSocket이 accept()로 client를 기다렸다가 들어오면 clientSocket에 넣어줘서 널이 아니게 된다.
            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");

                /**
                 * Step1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
                 * */

                try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()){
                    //InpuStream을 line by line 으로 읽고 싶기 때문에 BufferedReader로 바꿈
                    //InputStream -> InputStreamReader -> BufferedReader 순서로 감싸줌
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    //OutputStream을 DataOutputSteram으로 감싸줌
                    DataOutputStream dos = new DataOutputStream(out);

                    /*//http protocal 구경하기
                    String line;
                    while((line = br.readLine())!=""){
                        System.out.println(line);
                    }*/
                    /***
                     * GET / HTTP/1.1
                     * Host: localhost:8080
                     * Connection: Keep-Alive
                     * User-Agent: Apache-HttpClient/4.5.13 (Java/11.0.14.1)
                     * Accept-Encoding: gzip,deflate
                     * */

                    // 클라이언트로부터 입력된 것을 HttpRequest에 버퍼드리더로 전달하면
                    HttpRequest httpRequest = new HttpRequest(br);

                    // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
                    if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                        QueryStrings queryStrings = httpRequest.getQueryStrings();

                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                        byte[] body = String.valueOf(result).getBytes();

                        HttpResponse response = new HttpResponse(dos);
                        response.response200Header("application/json", body.length);
                        response.responseBody(body);
                    }


                }
            }
        }
    }
}
