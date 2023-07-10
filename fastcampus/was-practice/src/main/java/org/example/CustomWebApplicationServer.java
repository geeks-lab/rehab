package org.example;

import org.example.calculator.Calculator;
import org.example.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
                 * step2. 사용자의 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 * */
                //new Thread(new ClientRequestHandler(clientSocket)).start();

                /**
                 * 위와 같은 방식으로 클라이언트의 요청이 들어올 때마다 스레드를 생성하여 스택에 메모리를 할당하는 것은
                 * 메모리와 cpu에 큰 부담을 주어 요청이 몰려 리소스가 없으면 서버가 다운될 수도 있다.
                 * 따라서 아래와 같이 리팩도링을 한다.
                 * step3. Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.
                 * */

                executorService.execute(new ClientRequestHandler(clientSocket));



                /**
                 * Step1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
                 * */

                /*try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()){
                    //InpuStream을 line by line 으로 읽고 싶기 때문에 BufferedReader로 바꿈
                    //InputStream -> InputStreamReader -> BufferedReader 순서로 감싸줌
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    //OutputStream을 DataOutputSteram으로 감싸줌
                    DataOutputStream dos = new DataOutputStream(out);


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
                    }*/

                    /**
                     * 요구사항대로 메인스레드에서 작업을 진행했지만 메인스레드에서 작업을 진행하게 되면
                     * 만약 메인스레드에서 작업하고 있는데 블락킹이 된다면 다음 클라이언트 요청은
                     * 해당 작업이 끝날 때까지 기다려야 한다는 심각한 문제가 있다.
                     * 따라서 요청이 들어올 때마다 별도의 스레드에서 작업을 진행할 수 있도록 리팩도링을 한다
                     * -> step2. 사용자의 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                     *
                     * */



            }
        }
    }
}
