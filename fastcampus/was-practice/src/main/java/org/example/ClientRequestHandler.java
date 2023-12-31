package org.example;

import org.example.calculator.Calculator;
import org.example.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientRequestHandler implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);

    private final Socket clientSocket;

    public ClientRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * 요구사항대로 메인스레드에서 작업을 진행했지만 메인스레드에서 작업을 진행하게 되면
     * 만약 메인스레드에서 작업하고 있는데 블락킹이 된다면 다음 클라이언트 요청은
     * 해당 작업이 끝날 때까지 기다려야 한다는 심각한 문제가 있다.
     * 따라서 요청이 들어올 때마다 별도의 스레드에서 작업을 진행할 수 있도록 리팩도링을 한다
     * -> step2. 사용자의 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
     *
     * */

    @Override
    public void run() {
        logger.info("[ClientRequestHandler] new client {} started.", Thread.currentThread().getName());
        try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
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
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
